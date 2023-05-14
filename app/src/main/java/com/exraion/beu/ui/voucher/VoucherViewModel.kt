package com.exraion.beu.ui.voucher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.voucher.VoucherRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.VoucherList
import com.exraion.beu.util.UIState
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VoucherViewModel(
    private val repository: VoucherRepository
) : ViewModel() {

    private val _isShippingExpanded = MutableStateFlow(false)
    val isShippingExpended = _isShippingExpanded.asStateFlow()

    private val _isProductExpanded = MutableStateFlow(false)
    val isProductExpended = _isProductExpanded.asStateFlow()

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _shippingVoucherFullData = MutableStateFlow(listOf<VoucherList>())
    private val _shippingVoucher = MutableStateFlow(listOf<VoucherList>())
    val shippingVoucher = _shippingVoucher.asStateFlow()

    private val _productVoucherFullData = MutableStateFlow(listOf<VoucherList>())
    private val _productVoucher = MutableStateFlow(listOf<VoucherList>())
    val productVoucher = _productVoucher.asStateFlow()

    var message: String = ""

    init {
        _uiState.value = UIState.LOADING
        fetchAvailableVouchers()
    }

    fun expandShippingVouchers() {
        _isShippingExpanded.value = !_isShippingExpanded.value
        _isShippingExpanded.value then {
            _shippingVoucher.value = _shippingVoucherFullData.value
        } otherwise {
            _shippingVoucher.value = _shippingVoucherFullData.value.take(2)
        }
    }

    fun expandProductVouchers() {
        _isProductExpanded.value = !_isProductExpanded.value
        _isProductExpanded.value then {
            _productVoucher.value = _productVoucherFullData.value
        } otherwise {
            _productVoucher.value = _productVoucherFullData.value.take(2)
        }
    }

    fun fetchAvailableVouchers() = run {
        viewModelScope.launch {
            repository.fetchAvailableVouchers().collect {
                when(it) {
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _shippingVoucherFullData.value = it.data?.shipping ?: emptyList()
                        _productVoucherFullData.value = it.data?.product ?: emptyList()

                        _shippingVoucher.value = _shippingVoucherFullData.value.take(2)
                        _productVoucher.value = _productVoucherFullData.value.take(2)
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

}