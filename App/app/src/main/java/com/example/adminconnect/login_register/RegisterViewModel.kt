package com.example.adminconnect.login_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminconnect.login_register.ApiResponse
import com.example.adminconnect.login_register.RegisterRequest
import com.example.adminconnect.login_register.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableStateFlow<ApiResponse?>(null)
    val registerState: StateFlow<ApiResponse?> = _registerState

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.register(request)
                _registerState.value = response
            } catch (e: Exception) {
                _registerState.value = ApiResponse(success = false, message = e.message ?: "An error occurred")
            }
        }
    }
}
