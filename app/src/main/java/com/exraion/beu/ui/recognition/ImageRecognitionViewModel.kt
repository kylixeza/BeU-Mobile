package com.exraion.beu.ui.recognition

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

class ImageRecognitionViewModel(
    private val repository: MenuRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")

    private val _relatedMenus = MutableStateFlow<List<MenuList>>(emptyList())
    val relatedMenus = _relatedMenus.asStateFlow()

    var message = ""

    fun setQuery(query: String) {
        _query.value = query
    }

    fun fetchRelatedMenus() {
        _uiState.value = UIState.LOADING
        viewModelScope.launch {
            repository.fetchSearchedMenus("Fried Rice").collect {
                when(it) {
                    is Resource.Loading -> _uiState.value = UIState.LOADING
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _relatedMenus.value = it.data!!
                    }
                    else -> doNothing()
                }
            }
        }
    }

}