package com.example.adminconnect.login_register

import retrofit2.http.Body
import retrofit2.http.POST

data class OtpRequest(
    val email: String,
    val otp: String
)



data class RegisterRequest(
    val name: String,
    val phone: String,
    val course: String,
    val year: String,
    val rollNo: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class ApiResponse(val success: Boolean, val message: String)

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): ApiResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): ApiResponse

    @POST("request-otp") // Replace with the correct endpoint for requesting OTP
    suspend fun requestOtp(@Body emailRequest: Map<String, String>): ApiResponse

    @POST("verify-otp")
    suspend fun verifyOtp(@Body otpRequest: OtpRequest): ApiResponse
}

@POST("verify-otp")
suspend fun verifyOtp(@Body otpRequest: OtpRequest) {
}


