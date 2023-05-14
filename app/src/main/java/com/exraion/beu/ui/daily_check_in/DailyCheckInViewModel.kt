package com.exraion.beu.ui.daily_check_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.daily_xp.DailyXpRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.DailyXp
import com.exraion.beu.util.UIState
import com.exraion.beu.util.doNothing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DailyCheckInViewModel(
    private val repository: DailyXpRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()

    private val _dailyXps = MutableStateFlow<List<DailyXp>>(emptyList())
    val dailyXps = _dailyXps.asStateFlow()

    var message = ""

    init {
        _uiState.value = UIState.LOADING
        viewModelScope.launch {
            repository.fetchDailyXps().collect {
                when (it) {
                    is Resource.Success -> {
                        _dailyXps.value = it.data!!
                        _uiState.value = UIState.SUCCESS
                    }
                    is Resource.Empty -> {
                        _uiState.value = UIState.ERROR
                    }
                    else -> doNothing()
                }
            }
        }
    }

}