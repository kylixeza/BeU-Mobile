package com.exraion.beu.ui.voucher.my_voucher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.voucher.VoucherRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.VoucherList
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyVoucherViewModel(
    private val repository: VoucherRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _vouchers = MutableStateFlow(listOf<VoucherList>())
    val vouchers = _vouchers.asStateFlow()

    var message: String = ""

    init {
        viewModelScope.launch {
            repository.fetchUserVouchers().collect {
                when(it) {
                    is Resource.Loading -> _uiState.value = UIState.LOADING
                    is Resource.Empty -> _uiState.value = UIState.EMPTY
                    is Resource.Error -> message = it.message.orEmpty()
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _vouchers.value = it.data ?: emptyList()
                    }
                }
            }
        }
    }

}