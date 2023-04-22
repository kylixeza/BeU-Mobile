package com.exraion.beu.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.user.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUIState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.readPrefHaveRunAppBefore().collect { hasRun ->
                if (hasRun) {
                   repository.readPrefIsLogin().collect { isLogin ->
                        if (isLogin) {
                            repository.fetchUserDetail().collect {
                                _uiState.value = SplashUIState.LoggedIn
                            }
                        } else {
                            _uiState.value = SplashUIState.NotLoggedIn
                        }
                    }
                } else {
                    _uiState.value = SplashUIState.FirstRun
                }
            }
        }
    }

}

enum class SplashUIState {
    Idle,
    FirstRun,
    NotFirstRun,
    LoggedIn,
    NotLoggedIn
}