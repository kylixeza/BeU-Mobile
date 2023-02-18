package com.exraion.beu.ui.onboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnBoardingViewModel: ViewModel() {
    
    private val _pageIndex = MutableStateFlow(0)
    val pageIndex = _pageIndex.asStateFlow()
    
    fun previousPageTo(index: Int) {
        _pageIndex.value = index
    }
    
}