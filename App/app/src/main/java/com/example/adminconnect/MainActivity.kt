package com.example.adminconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.adminconnect.ui.theme.AdminConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdminConnectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AdminConnect(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AdminConnect(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",   // if not using splash startDestination is "login"
        modifier = Modifier.fillMaxSize()
    ) {
        composable("splash") {
            SplashScreen(onTimeout = { navController.navigate("login") })
        }

        composable("login") {
            LoginScreen(
                onNavigateToDashboard = { navController.navigate("dashboard") },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(onNavigateToOTP = {navController.navigate("otp")})
        }
        composable("otp") {
            OTPscreen{ navController.navigate("dashboard")}
        }
        composable("dashboard") {
            DashboardScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminConnectPreview() {

}