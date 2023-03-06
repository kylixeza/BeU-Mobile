package com.exraion.beu.ui.detail.ingredient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.menu.MenuRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.Ingredient
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val repository: MenuRepository
) : ViewModel() {
    
    private val _ingredients = MutableStateFlow<List<Ingredient>>(emptyList())
    val ingredients = _ingredients.asStateFlow()
    
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()
    
    var message = ""
    
    fun fetchIngredients(menuId: String) {
        viewModelScope.launch {
            repository.fetchMenuIngredients(menuId).collect {
                when(it) {
                    is Resource.Loading -> _uiState.value = UIState.LOADING
                    is Resource.Empty -> _uiState.value = UIState.EMPTY
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message.toString()
                    }
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _ingredients.value = it.data ?: emptyList()
                    }
                }
            }
        }
    }
}