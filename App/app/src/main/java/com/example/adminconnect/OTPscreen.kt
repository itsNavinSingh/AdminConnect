package com.example.adminconnect

import OtpViewModel
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminconnect.login_register.OtpRequest

@Composable
fun OTPscreen(
    modifier: Modifier = Modifier,
    onNavigateToDashboard:() -> Unit,
    otpViewModel: OtpViewModel = viewModel()
) {

    val otpVerificationState by otpViewModel.otpVerificationState.collectAsState()

    // Check verification state for navigation
    if (otpVerificationState?.success == true) {
        onNavigateToDashboard()
    }

    val isClicked = remember { mutableStateOf(false) }
    val emailId = remember { mutableStateOf("") }
    val otp = remember { mutableStateOf("") }
    // States for user inputs
    val cPassword = remember { mutableStateOf("") }
    val isOtpRequested = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        color = Color.White
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 120.dp, bottom = 120.dp, start = 30.dp, end = 30.dp),
            shape = RoundedCornerShape(corner = CornerSize(15.dp)),
            colors = CardDefaults.cardColors(containerColor = Color.White), // For background color
            border = BorderStroke(2.dp,Color.Gray),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // For elevation
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text("Register", fontWeight = FontWeight.Bold, fontSize = 34.sp,fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.height(40.dp))

                //email input
                OutlinedTextField(emailId.value, onValueChange = {emailId.value = it}, label = { Text("College Email Id") })
                Spacer(modifier = Modifier.height(10.dp))

                //password input
                OutlinedTextField(cPassword.value, onValueChange = {cPassword.value = it}, label = { Text("Create Password") })
                Spacer(modifier = Modifier.height(20.dp))

//                Button(
//                    onClick = { isClicked.value = !isClicked.value },
////                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
//                ) {
//                    Text("Verify")
//                }
//
//                if (isClicked.value) {
//                    Column() {
//                        Text("Enter OTP",modifier = Modifier.align(Alignment.Start).padding(5.dp))
//                        OutlinedTextField(otp.value, onValueChange = {otp.value = it})
//                    }
//                    Spacer(modifier = Modifier.height(20.dp))
//
//                    Button(
//                        onClick = { onNavigateToDashboard() },
////                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
//                    ) {
//                        Text("Next")
//                    }
//                }

                // Request OTP Button
                Button(
                    onClick = {
                        isOtpRequested.value = true
                        otpViewModel.requestOtp(emailId.value) // Request OTP via ViewModel
                    }
                ) {
                    Text("Request OTP")
                }

                if (isOtpRequested.value) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Column {
                        Text("Enter OTP", modifier = Modifier.align(Alignment.Start).padding(5.dp))
                        OutlinedTextField(
                            value = otp.value,
                            onValueChange = { otp.value = it },
                            label = { Text("OTP") }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Verify OTP Button
                    Button(
                        onClick = {
                            otpViewModel.verifyOtp(emailId.value, otp.value) // Verify OTP
                        }
                    ) {
                        Text("Verify OTP")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OTPscreenPreview() {
//    OTPscreen(modifier = Modifier) {}
}
