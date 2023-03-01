package com.exraion.beu.ui.detail.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.menu.MenuRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.MenuDetail
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailMenuViewModel(
    private val repository: MenuRepository
) : ViewModel() {
    
    var menuId: String = ""
    
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()
    
    private val _menuDetail = MutableStateFlow<MenuDetail?>(null)
    val menuDetail = _menuDetail.asStateFlow()
    
    var message: String = ""
    
    fun getMenuDetail() {
        viewModelScope.launch {
            repository.fetchMenuDetail(menuId).collect {
                when(it) {
                    is Resource.Empty -> _uiState.value = UIState.EMPTY
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message.toString()
                    }
                    is Resource.Loading -> _uiState.value = UIState.LOADING
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _menuDetail.value = it.data
                    }
                }
            }
        }
    }
    
    
}