package com.exraion.beu.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.util.Resource
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private var email: String = ""
    private var password: String = ""
    var message: String = ""
    
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()
    
    fun setEmail(email: String) {
        this.email = email
    }
    
    fun setPassword(password: String) {
        this.password = password
    }
    
    fun login() {
        val body = LoginBody(
            email = email,
            password = password
        )
        viewModelScope.launch {
            repository.signIn(body).collect {
                when(it) {
                    is Resource.Success -> _uiState.value = UIState.SUCCESS
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message.toString()
                    }
                    is Resource.Loading -> _uiState.value = UIState.LOADING
                    else -> _uiState.value = UIState.ERROR
                }
            }
        }
    }
    
    fun savePrefIsLogin(isLogin: Boolean) = viewModelScope.launch {
        repository.savePrefIsLogin(isLogin)
    }
    
    fun savePrefHaveRunAppBefore(isFirstTime: Boolean) = viewModelScope.launch {
        repository.savePrefHaveRunAppBefore(isFirstTime)
    }
}