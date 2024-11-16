package com.example.adminconnect

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adminconnect.login_register.LoginRequest
import com.example.adminconnect.login_register.LoginViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onNavigateToRegister:() -> Unit,
    onNavigateToDashboard: () -> Unit,
    loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val loginState by loginViewModel.loginState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val emailId = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }

        Text("AdminConnect", fontWeight = FontWeight.Bold, fontSize = 34.sp,fontStyle = FontStyle.Italic)
        Spacer(modifier = Modifier.height(40.dp))

        Column() {
            Text("College Email ID",modifier = Modifier.align(Alignment.Start).padding(5.dp))
            OutlinedTextField(emailId.value, onValueChange = {emailId.value = it})
        }
        Spacer(modifier = Modifier.height(30.dp))

        Column() {
            Text("Password",modifier = Modifier.align(Alignment.Start).padding(5.dp))
            OutlinedTextField(password.value, onValueChange = {password.value = it})
        }
        Spacer(modifier = Modifier.height(20.dp))

//        Button(
//            onClick = { onNavigateToDashboard() },
////            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
//        ) {
//            Text("Login")
//        }
        Button(onClick = {
            val request = LoginRequest(email = emailId.value, password = password.value)
            loginViewModel.login(request)
        }) {
            Text("Login")
        }

        loginState?.let {
            if (it.success) {
                onNavigateToDashboard()
            } else {
                Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
            }
        }

        Text("OR", modifier = Modifier.padding(8.dp))

        Row {
            Text("Don't have an account? ")
            Text("Sign up", color = Color.Blue, modifier = Modifier.clickable { onNavigateToRegister() })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(modifier = Modifier, onNavigateToRegister = {}, onNavigateToDashboard = {})

}