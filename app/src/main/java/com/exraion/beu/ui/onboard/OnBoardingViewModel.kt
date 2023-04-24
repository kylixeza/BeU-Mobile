package com.exraion.beu.ui.onboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardingViewModel: ViewModel() {

    private val _page = MutableStateFlow(1)
    val page = _page.asStateFlow()

    private val _isMaxPage = MutableStateFlow(false)
    val isMaxPage = _isMaxPage.asStateFlow()

    private val _isMinPage = MutableStateFlow(false)
    val isMinPage = _isMinPage.asStateFlow()

    fun setPage(page: Int) {
        _page.value = page
    }

    fun nextPage() {
        if (_page.value == 3) {
            _isMaxPage.value = true
            return
        }
        _isMinPage.value = false
        _page.value = _page.value + 1
    }

    fun prevPage() {
        if (_page.value == 1) {
            _isMinPage.value = true
            return
        }
        _isMaxPage.value = false
        _page.value = _page.value - 1
    }
    
}