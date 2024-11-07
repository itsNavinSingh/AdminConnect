package com.example.adminconnect

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
import androidx.compose.foundation.layout.width
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
fun RegisterScreen(modifier: Modifier = Modifier, onNavigateToOTP:() -> Unit) {

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
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val name = remember { mutableStateOf("")}
                val phone = remember { mutableStateOf("") }
                val course = remember { mutableStateOf("")}
                val year = remember { mutableStateOf("")}
                val rollNo = remember { mutableStateOf("")}

                Text("Register", fontWeight = FontWeight.Bold, fontSize = 34.sp,fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(name.value, onValueChange = {name.value = it}, label = { Text("Name") })
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(phone.value, onValueChange = {phone.value = it}, label = { Text("Phone no.") })
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(course.value, onValueChange = {course.value = it}, label = { Text("Course") })
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(year.value, onValueChange = {year.value = it}, label = { Text("Year") })
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(rollNo.value, onValueChange = {rollNo.value = it}, label = { Text("College Roll no.") })
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onNavigateToOTP() },
//                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen(modifier = Modifier) {}
}