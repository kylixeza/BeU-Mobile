package com.exraion.beu.ui.voucher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.voucher.VoucherRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.VoucherList
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VoucherViewModel(
    private val repository: VoucherRepository
) : ViewModel() {

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded = _isExpanded.asStateFlow()

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _shippingVoucher = MutableStateFlow(listOf<VoucherList>())
    val shippingVoucher = _shippingVoucher.asStateFlow()

    private val _productVoucher = MutableStateFlow(listOf<VoucherList>())
    val productVoucher = _productVoucher.asStateFlow()

    var message: String = ""

    init {
        _uiState.value = UIState.LOADING
        viewModelScope.launch {
            repository.fetchAvailableVouchers().collect {
                when(it) {
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _shippingVoucher.value = it.data?.shipping ?: emptyList()
                        _productVoucher.value = it.data?.product ?: emptyList()
                    }
                    is Resource.Empty -> _uiState.value = UIState.EMPTY
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message.orEmpty()
                    }
                    else -> {}
                }
            }
        }
    }

    fun showMoreItems() {
        _isExpanded.value = !_isExpanded.value
    }

}