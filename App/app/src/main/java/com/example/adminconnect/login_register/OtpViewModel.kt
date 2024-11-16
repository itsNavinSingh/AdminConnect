import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.adminconnect.login_register.ApiResponse
import com.example.adminconnect.login_register.ApiService
import com.example.adminconnect.login_register.OtpRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OtpViewModel(private val apiService: ApiService) : ViewModel() {

    // Backing property to avoid exposing a mutable flow
    private val _otpVerificationState = MutableStateFlow<ApiResponse?>(null)
    val otpVerificationState: StateFlow<ApiResponse?> = _otpVerificationState.asStateFlow()

    fun requestOtp(email: String) {
        viewModelScope.launch {
            try {
                val response = apiService.requestOtp(mapOf("email" to email))
                _otpVerificationState.value = response
            } catch (e: Exception) {
                _otpVerificationState.value = ApiResponse(false, e.message ?: "Error")
            }
        }
    }

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            try {
                val response = apiService.verifyOtp(OtpRequest(email, otp))
                _otpVerificationState.value = response
            } catch (e: Exception) {
                _otpVerificationState.value = ApiResponse(false, e.message ?: "Error")
            }
        }
    }
}
