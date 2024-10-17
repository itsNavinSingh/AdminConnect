package com.example.adminconnect

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun OTPscreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val cPassword = remember { mutableStateOf("") }
        val phone = remember { mutableStateOf("") }
        val otp = remember { mutableStateOf("") }

        Text("Register", fontWeight = FontWeight.Bold, fontSize = 34.sp,fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(40.dp))

        Column() {
            Text("Create Password",modifier = Modifier.align(Alignment.Start).padding(5.dp))
            OutlinedTextField(cPassword.value, onValueChange = {cPassword.value = it})
        }
        Spacer(modifier = Modifier.height(20.dp))

        Column() {
            Text("Phone no. ",modifier = Modifier.align(Alignment.Start).padding(5.dp))
            OutlinedTextField(phone.value, onValueChange = {phone.value = it})
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Verify")
        }

        Column() {
            Text("Enter OTP",modifier = Modifier.align(Alignment.Start).padding(5.dp))
            OutlinedTextField(otp.value, onValueChange = {otp.value = it})
        }
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text("Next")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OTPscreenPreview() {
    OTPscreen()
}
