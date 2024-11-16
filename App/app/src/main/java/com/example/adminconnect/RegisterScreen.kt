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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.adminconnect.login_register.ApiResponse
import com.example.adminconnect.login_register.RegisterRequest
import com.example.adminconnect.login_register.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onNavigateToOTP:(ApiResponse) -> Unit,
    registerViewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val registerState by registerViewModel.registerState.collectAsState()

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
//                val course = remember { mutableStateOf("")}
//                val year = remember { mutableStateOf("")}
                val courseOptions = listOf("BSc (H) Computer Science", "BSc (H) Mathematics", "BSc (H) Statistics","BSc (H) Geology","BSc (H) Microbiology","BMS","BA Program (Eco + CS)","BA Program (Eco + Maths)","BA Program (His + Pol Sci)","BA (H) English","BA (H) Hindi","BA (H) History","BA (H) Political Science","B.Com (H)","B.Com (Program)","BJMC")
                val yearOptions = listOf("2018", "2019", "2020","2021","2022", "2023", "2024","2025")
                val selectedCourse = remember { mutableStateOf("") }
                val expandedCourse = remember { mutableStateOf(false) }
                val selectedYear = remember { mutableStateOf("") }
                val expandedYear = remember { mutableStateOf(false) }
                val rollNo = remember { mutableStateOf("")}

                Text("Register", fontWeight = FontWeight.Bold, fontSize = 34.sp,fontStyle = FontStyle.Italic)
                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(name.value, onValueChange = {name.value = it}, label = { Text("Name") })
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(phone.value, onValueChange = {phone.value = it}, label = { Text("Phone no.") })
                Spacer(modifier = Modifier.height(10.dp))

//                OutlinedTextField(course.value, onValueChange = {course.value = it}, label = { Text("Course") })
//                Spacer(modifier = Modifier.height(10.dp))
//
//                OutlinedTextField(year.value, onValueChange = {year.value = it}, label = { Text("Year") })
//                Spacer(modifier = Modifier.height(10.dp))

                // Dropdown for Course
                ExposedDropdownMenuBox(
                    expanded = expandedCourse.value,
                    onExpandedChange = { expandedCourse.value = !expandedCourse.value }
                ) {
                    OutlinedTextField(
                        value = selectedCourse.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Course") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCourse.value)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .clickable { expandedCourse.value = !expandedCourse.value }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedCourse.value,
                        onDismissRequest = { expandedCourse.value = false }
                    ) {
                        courseOptions.forEach { course ->
                            DropdownMenuItem(
                                text = { Text(course) },
                                onClick = {
                                    selectedCourse.value = course
                                    expandedCourse.value = false
                                }
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))

                // Dropdown for Year
                ExposedDropdownMenuBox(
                    expanded = expandedYear.value,
                    onExpandedChange = { expandedYear.value = !expandedYear.value }
                ) {
                    OutlinedTextField(
                        value = selectedYear.value,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Year") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedYear.value)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .clickable { expandedYear.value = !expandedYear.value }
                    )
                    ExposedDropdownMenu(
                        expanded = expandedYear.value,
                        onDismissRequest = { expandedYear.value = false }
                    ) {
                        yearOptions.forEach { year ->
                            DropdownMenuItem(
                                text = { Text(year) },
                                onClick = {
                                    selectedYear.value = year
                                    expandedYear.value = false
                                }
                            )
                        }
                    }
                }
                                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(rollNo.value, onValueChange = {rollNo.value = it}, label = { Text("College Roll no.") })
                Spacer(modifier = Modifier.height(20.dp))

//                Button(
//                    onClick = { onNavigateToOTP() },
////                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
//                ) {
//                    Text("Next")
//                }
                Button(onClick = {
                    val request = RegisterRequest(
                        name = name.value,
                        phone = phone.value,
                        course = selectedCourse.value,
                        year = selectedYear.value,
                        rollNo = rollNo.value,
                        email = "user@example.com", // Add email logic
                        password = "password123"    // Add password logic
                    )
                    registerViewModel.register(request)
                }) {
                    Text("Next")
                }

                registerState?.let {
                    if (it.success) {
                        onNavigateToOTP(it)
                    } else {
                        Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
//    RegisterScreen(modifier = Modifier) {}
}