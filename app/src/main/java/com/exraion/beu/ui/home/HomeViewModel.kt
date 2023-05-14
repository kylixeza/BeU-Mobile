package com.exraion.beu.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.daily_xp.DailyXpRepository
import com.exraion.beu.data.repository.menu.MenuRepository
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.source.remote.api.model.dailyxp.DailyXpRequest
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.MenuList
import com.exraion.beu.model.User
import com.exraion.beu.util.UIState
import com.exraion.beu.util.doNothing
import com.exraion.beu.util.then
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository,
    private val dailyXpRepository: DailyXpRepository
) : ViewModel() {
    
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()
    
    private val _menu = MutableStateFlow<List<MenuList>>(emptyList())
    val menu = _menu.asStateFlow()
    
    private val _dietMenus = MutableStateFlow<List<MenuList>>(emptyList())
    val dietMenus = _dietMenus.asStateFlow()
    
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _dailyXpUiState = MutableStateFlow(UIState.IDLE)
    val dailyXpUiState = _dailyXpUiState.asStateFlow()

    private val _isDailyXpTaken = MutableStateFlow<Boolean?>(null)
    val isDailyXpTaken = _isDailyXpTaken.asStateFlow()

    var message = ""
    val categories = listOf("All", "Meat", "Vegetables", "Rice", "Soup", "Cake", "Snacks")
    
    init {
        viewModelScope.launch {
            userRepository.getUserDetail().collect {
                _user.value = it.data
            }
            
            _uiState.value = UIState.LOADING
            menuRepository.fetchMenus().zip(
                menuRepository.fetchDietMenus()
            ) { menus, dietMenus ->
                Pair(menus, dietMenus)
            }.collect {
                when (it.first) {
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _menu.value = it.first.data ?: listOf()
                        _dietMenus.value = it.second.data ?: listOf()
                    }
                    is Resource.Empty -> {
                        _uiState.value = UIState.EMPTY
                    }
                    is Resource.Error -> {
                        message = it.first.message ?: ""
                        _uiState.value = UIState.ERROR
                    }
                    else -> {
                        _uiState.value = UIState.LOADING
                    }
                }
            }
        }
    }

    fun takeDailyXp() {
        _dailyXpUiState.value = UIState.LOADING
        viewModelScope.launch {
            dailyXpRepository.fetchTodayDailyXp().collect {dailyXp ->
                when(dailyXp) {
                    is Resource.Success -> {
                        _isDailyXpTaken.value = dailyXp.data?.isTaken
                        _isDailyXpTaken.value?.not()?.then {
                            viewModelScope.launch {
                                val body = DailyXpRequest(dailyXp.data?.dailyXpId.orEmpty())
                                dailyXpRepository.takeDailyXp(body).collect {
                                    when(it) {
                                        is Resource.Success -> {
                                            async { userRepository.increaseUserXp(dailyXp.data!!.dailyXp) }.await()
                                            _dailyXpUiState.value = UIState.SUCCESS
                                        }
                                        is Resource.Error -> {
                                            message = it.message.orEmpty()
                                            _dailyXpUiState.value = UIState.ERROR
                                        }
                                        else -> doNothing()
                                    }
                                }
                            }
                        }
                        _dailyXpUiState.value = UIState.IDLE
                        _isDailyXpTaken.value = null
                    }
                    is Resource.Error -> {
                        message = dailyXp.message.orEmpty()
                        _dailyXpUiState.value = UIState.ERROR
                    }
                    else -> doNothing()
                }
            }
        }
    }
}