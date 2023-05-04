package com.exraion.beu.ui.detail.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.repository.voucher.VoucherRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.User
import com.exraion.beu.model.VoucherDetail
import com.exraion.beu.model.VoucherList
import com.exraion.beu.util.UIState
import com.exraion.beu.util.VoucherCategory
import com.exraion.beu.util.doNothing
import com.exraion.beu.util.isEqualTo
import com.exraion.beu.util.isGreaterThan
import com.exraion.beu.util.isLessThanOrEqual
import com.exraion.beu.util.otherwise
import com.exraion.beu.util.then
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OrderViewModel(
    private val userRepository: UserRepository,
    private val voucherRepository: VoucherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _price = MutableStateFlow(0)
    val price = _price.asStateFlow()

    private val _shippingCost = MutableStateFlow(listOf(3000, 5000, 9000, 11000).random())
    val shippingCost = _shippingCost.asStateFlow()

    private val _discount = MutableStateFlow(0)
    val discount = _discount.asStateFlow()

    private val _admin = MutableStateFlow(listOf(1000, 2000, 3000, 4000).random())
    val admin = _admin.asStateFlow()

    private val _total = MutableStateFlow(0)
    val total = _total.asStateFlow()

    private val _vouchers = MutableStateFlow(listOf<VoucherList>())
    val vouchers = _vouchers.asStateFlow()

    private val _isVoucherApplied = MutableStateFlow(false)
    val isVoucherApplied = _isVoucherApplied.asStateFlow()

    private val _isVoucherCanBeApplied = MutableStateFlow(false)
    val isVoucherCanBeApplied = _isVoucherCanBeApplied.asStateFlow()

    private val _selectedVoucher = MutableStateFlow<VoucherDetail?>(null)
    val selectedVoucher = _selectedVoucher.asStateFlow()

    var message = ""

    init {
        viewModelScope.launch {
            _uiState.value = UIState.LOADING
            userRepository.getUserDetail().collect {
                when(it) {
                    is Resource.Success -> _user.value = it.data
                    else -> doNothing()
                }
            }
        }
    }

    fun setPrice(price: Int) {
        _price.value = price
        _total.value = price + _shippingCost.value + _admin.value - _discount.value
    }

    fun applyVoucher(voucher: VoucherDetail) {
        _selectedVoucher.value = voucher
        voucher.category isEqualTo VoucherCategory.PRODUCT.category then {
            _price.value isGreaterThan voucher.minimumSpend then {
                ((_price.value * voucher.discount) / 100) isLessThanOrEqual voucher.maximumDiscount then {
                    _discount.value = (_price.value * voucher.discount) / 100
                } otherwise {
                    _discount.value = voucher.maximumDiscount
                }
                _isVoucherApplied.value = true
                _isVoucherCanBeApplied.value = true
                _total.value = _price.value + _shippingCost.value + _admin.value - voucher.discount
            } otherwise {
                _isVoucherCanBeApplied.value = false
                message = "Voucher tidak dapat digunakan"
            }
        } otherwise {
            voucher.discount isEqualTo 100 then {
                _shippingCost.value = 0
            } otherwise {
                _shippingCost.value = (_shippingCost.value * voucher.discount) / 100
            }
            _isVoucherApplied.value = true
            _isVoucherCanBeApplied.value = true
        }
    }

    fun getUserVouchers() {
        _uiState.value = UIState.LOADING
        viewModelScope.launch {
            voucherRepository.fetchUserVouchers().collectLatest {
                when(it) {
                    is Resource.Success -> _vouchers.value = it.data!!
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message!!
                    }
                    else -> doNothing()
                }
            }
        }
    }
}