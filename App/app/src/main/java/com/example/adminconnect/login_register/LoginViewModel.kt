package com.example.adminconnect.login_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminconnect.login_register.ApiResponse
import com.example.adminconnect.login_register.LoginRequest
import com.example.adminconnect.login_register.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<ApiResponse?>(null)
    val loginState: StateFlow<ApiResponse?> = _loginState

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.login(request)
                _loginState.value = response
            } catch (e: Exception) {
                _loginState.value = ApiResponse(success = false, message = e.message ?: "An error occurred")
            }
        }
    }
}
