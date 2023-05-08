package com.exraion.beu.ui.detail.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.menu.MenuRepository
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.source.remote.api.model.favorite.FavoriteBody
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.MenuDetail
import com.exraion.beu.util.UIState
import com.exraion.beu.util.doNothing
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailMenuViewModel(
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {
    
    private val _menuId = MutableStateFlow<String?>(null)
    val menuId = _menuId.asStateFlow()
    
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()
    
    private val _menuDetail = MutableStateFlow<MenuDetail?>(null)
    val menuDetail = _menuDetail.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()
    
    var message: String = ""

    fun setMenuId(menuId: String) {
        _menuId.value = menuId
    }

    fun getMenuDetail() {
        _uiState.value = UIState.LOADING
        viewModelScope.launch {
            menuRepository.fetchMenuDetail(_menuId.value!!).collect {
                when(it) {
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message.toString()
                    }
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _menuDetail.value = it.data
                        _isFavorite.value = it.data?.isFavorite ?: false
                    }

                    else -> doNothing()
                }
            }
        }
    }

    fun toggleFavorite() {
        _isFavorite.value then { removeFavorite() } otherwise { insertFavorite() }
    }

    private fun insertFavorite() {
        viewModelScope.launch {
            userRepository.postFavorite(FavoriteBody(_menuId.value!!)).collectLatest {
                when(it) {
                    is Resource.Success -> _isFavorite.value = true
                    is Resource.Error -> {
                        _isFavorite.value = false
                        message = it.message.toString()
                    }
                    else -> doNothing()
                }
            }
        }
    }

    private fun removeFavorite() {
        viewModelScope.launch {
            userRepository.deleteFavorite(_menuId.value!!).collectLatest {
                when(it) {
                    is Resource.Success -> _isFavorite.value = false
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message.toString()
                    }
                    else -> doNothing()
                }
            }
        }
    }
}