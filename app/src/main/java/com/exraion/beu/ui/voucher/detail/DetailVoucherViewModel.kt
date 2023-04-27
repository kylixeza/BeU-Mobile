package com.exraion.beu.ui.voucher.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.repository.voucher.VoucherRepository
import com.exraion.beu.data.source.dummy.getProductVoucherTermsAndConditions
import com.exraion.beu.data.source.dummy.getShippingVoucherTermsAndConditions
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.VoucherDetail
import com.exraion.beu.util.UIState
import com.exraion.beu.util.VoucherCategory
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailVoucherViewModel(
    private val userRepository: UserRepository,
    private val voucherRepository: VoucherRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _voucher = MutableStateFlow<VoucherDetail?>(null)
    val voucher = _voucher.asStateFlow()

    private val _termsAndConditions = MutableStateFlow<List<String>>(emptyList())
    val termsAndConditions = _termsAndConditions.asStateFlow()

    private val _isXpEnough = MutableStateFlow<Boolean?>(null)
    val isXpEnough = _isXpEnough.asStateFlow()

    private val _isXpSuccessfullyRedeemed = MutableStateFlow<Pair<Boolean, String?>?>(null)
    val isXpSuccessfullyRedeemed = _isXpSuccessfullyRedeemed.asStateFlow()

    var message = ""

    fun fetchVoucherDetail(voucherId: String) {
        viewModelScope.launch {
            _uiState.value = UIState.LOADING
            voucherRepository.fetchVoucherDetail(voucherId).collectLatest {
                when(it) {
                    is Resource.Error -> {
                        message = it.message.toString()
                        _uiState.value = UIState.ERROR
                    }
                    is Resource.Success -> {
                        _voucher.value = it.data
                        if (it.data?.category == VoucherCategory.PRODUCT.category)
                            _termsAndConditions.value = it.data.getProductVoucherTermsAndConditions()
                        else
                            _termsAndConditions.value = it.data!!.getShippingVoucherTermsAndConditions()
                        _uiState.value = UIState.SUCCESS
                    }
                    else -> {}
                }
            }
        }
    }

    fun checkIsXpEnough() {
        viewModelScope.launch {
            val userXp = async { userRepository.getUserXp().first() }.await()
            val costXp = voucher.value!!.xpCost
            _isXpEnough.value = userXp >= costXp
        }
    }

    fun redeemVoucher() {
        viewModelScope.launch {
            val costXp = voucher.value!!.xpCost
            voucherRepository.redeemVoucher(voucher.value!!.voucherId, costXp).collectLatest {
                when(it) {
                    is Resource.Error -> {
                        _isXpSuccessfullyRedeemed.value = false to it.message
                    }
                    is Resource.Success -> {
                        _isXpSuccessfullyRedeemed.value = true to null
                    }
                    else -> {}
                }
            }
        }
    }

}