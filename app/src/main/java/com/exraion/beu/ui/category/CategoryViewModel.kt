package com.exraion.beu.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.menu.MenuRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.MenuList
import com.exraion.beu.util.UIState
import com.exraion.beu.util.doNothing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: MenuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _menus = MutableStateFlow(listOf<MenuList>())
    val menus = _menus.asStateFlow()

    var message: String = ""

    fun fetchCategorizedMenus(category: String) {
        viewModelScope.launch {
            _uiState.value = UIState.LOADING
            repository.fetchCategorizedMenus(category).collect {
                when(it) {
                    is Resource.Success -> {
                        _menus.value = it.data ?: listOf()
                        _uiState.value = UIState.SUCCESS
                    }
                    is Resource.Error -> {
                        message = it.message ?: ""
                        _uiState.value = UIState.ERROR
                    }
                    else -> doNothing()
                }
            }
        }
    }

}