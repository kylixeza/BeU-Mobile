package com.exraion.beu.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.menu.MenuRepository
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.MenuList
import com.exraion.beu.model.User
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {
    
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()
    
    private val _menu = MutableStateFlow<List<MenuList>>(emptyList())
    val menu = _menu.asStateFlow()
    
    private val _dietMenus = MutableStateFlow<List<MenuList>>(emptyList())
    val dietMenus = _dietMenus.asStateFlow()
    
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()
    
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
}