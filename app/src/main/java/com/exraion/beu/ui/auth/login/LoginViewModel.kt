package com.exraion.beu.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.util.Resource
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
        _uiState.value = UIState.LOADING
        val body = LoginBody(
            email = email,
            password = password
        )
        viewModelScope.launch {
            repository.signIn(body).collectLatest {
                when(it) {
                    is Resource.Success -> {
                        repository.fetchUserDetail().collectLatest {
                            when(it) {
                                is Resource.Success -> {
                                    _uiState.value = UIState.SUCCESS
                                    repository.savePrefIsLogin(true)
                                    repository.savePrefHaveRunAppBefore(true)
                                }
                                is Resource.Error -> {
                                    message = it.message.toString()
                                    _uiState.value = UIState.ERROR
                                }
                                else -> {}
                            }
                        }
                    }
                    is Resource.Error -> {
                        message = it.message.toString()
                        _uiState.value = UIState.ERROR
                    }
                    else -> {}
                }
            }
        }
    }
}