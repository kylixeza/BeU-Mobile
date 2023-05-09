package com.exraion.beu.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.order.OrderRepository
import com.exraion.beu.data.source.remote.api.model.history.HistoryUpdateStarsGiven
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.History
import com.exraion.beu.util.UIState
import com.exraion.beu.util.doNothing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val repository: OrderRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _histories = MutableStateFlow(emptyList<History>())
    val histories = _histories.asStateFlow()

    var message = ""

    init {
        _uiState.value = UIState.LOADING
        viewModelScope.launch {
            fetchUserOrders()
        }
    }

    fun cancelOrder(orderId: String) {
        viewModelScope.launch {
            repository.cancelOrder(orderId).collect {
                when(it) {
                    is Resource.Success -> fetchUserOrders()
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message!!
                    }
                    else -> doNothing()
                }
            }
        }
    }

    fun rateOrder(orderId: String, stars: Double) {
        _uiState.value = UIState.LOADING
        val body = HistoryUpdateStarsGiven(stars)
        viewModelScope.launch {
            repository.rateOrder(orderId, body).collect {
                when(it) {
                    is Resource.Success -> fetchUserOrders()
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message!!
                    }
                    else -> doNothing()
                }
            }
        }
    }

    private suspend fun fetchUserOrders() {
        repository.fetchUserOrders().collect {
            when(it) {
                is Resource.Success -> {
                    _uiState.value = UIState.SUCCESS
                    _histories.value = it.data!!
                }
                is Resource.Error -> {
                    _uiState.value = UIState.ERROR
                    message = it.message!!
                }
                is Resource.Empty -> _uiState.value = UIState.EMPTY
                else -> doNothing()
            }
        }

    }

}