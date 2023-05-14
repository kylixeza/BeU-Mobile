package com.exraion.beu.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.daily_xp.DailyXpRepository
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.util.doNothing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userRepository: UserRepository,
    private val dailyXpRepository: DailyXpRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUIState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.readPrefHaveRunAppBefore().collect { hasRun ->
                if (hasRun) {
                   userRepository.readPrefIsLogin().collect { isLogin ->
                        if (isLogin) {
                            userRepository.fetchUserDetail().zip(
                                dailyXpRepository.checkDailyXp()
                            ) { user, dailyXp ->
                                Pair(user, dailyXp)
                            }.collect {
                                when(it.first) {
                                    is Resource.Success -> _uiState.value = SplashUIState.LoggedIn
                                    is Resource.Error -> _uiState.value = SplashUIState.LoggedIn
                                    else -> doNothing()
                                }
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