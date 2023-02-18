package com.exraion.beu.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.util.Resource
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()
    
    private var email: String = ""
    private var password: String = ""
    private var name: String = ""
    private var phone: String = ""
    private var location: String = ""
    var message: String = ""
    
    fun setEmail(email: String) {
        this.email = email
    }
    
    fun setPassword(password: String) {
        this.password = password
    }
    
    fun setName(name: String) {
        this.name = name
    }
    
    fun setPhone(phone: String) {
        this.phone = phone
    }
    
    fun setLocation(location: String) {
        this.location = location
    }
    
    fun register() {
        val body = RegisterBody(
            email = email,
            password = password,
            name = name,
            phoneNumber = phone,
            location = location
        )
        viewModelScope.launch {
            repository.signUp(body).collect {
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