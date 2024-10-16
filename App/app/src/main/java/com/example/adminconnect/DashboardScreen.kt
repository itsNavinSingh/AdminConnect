package com.example.adminconnect

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = {
            BottomAppBar(
                content = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        BottomAppBarIcon(
                            icon = Icons.Filled.Send,
                            onClick = { /* Handle click */ },
                            label = "Ongoing"
                        )
                        BottomAppBarIcon(
                            icon = Icons.Filled.Refresh,
                            onClick = { /* Handle click */ },
                            label = "History"
                        )
                        BottomAppBarIcon(
                            icon = Icons.Filled.Add,
                            onClick = { /* Handle click */ },
                            label = "Create"
                        )
                    }
                }
            )
        }
    ) {  innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Profile_info()

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp)) {
                Text("Ongoing", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            }

            val ongoingQueries = listOf(
                Queries(275, "12/3/2024", "Received"),
                Queries(157, "11/4/2024", "Received"),
                Queries(160, "21/3/2024", "Received"),
                Queries(364, "10/2/2024", "Received"),
                Queries(732, "09/3/2024", "Received"),
                Queries(178, "08/1/2024", "Received"),
                Queries(679, "12/3/2024", "Received"),
                Queries(243, "23/2/2024", "Received"),
                Queries(689, "31/2/2024", "Received")
            )

            List_of_queries(data = ongoingQueries)
        }
    }
}

@Composable
fun BottomAppBarIcon(
    icon: ImageVector,
    onClick: () -> Unit,
    label: String
) {
    IconButton(onClick = onClick,
        modifier = Modifier
            .fillMaxHeight()
            .width(65.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(icon, contentDescription = null)
            Text(text = label)
        }
    }
}

@Composable
fun Profile_info() {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Surface(
            modifier = Modifier
                .size(70.dp)
                .padding(10.dp),
            shape = CircleShape,
        ) {
            Image(
                painter = painterResource(id = R.drawable.profileimg),
                contentDescription = "profile image",
            )
        }
        Column {
            Text("Avinash Madkatte", fontWeight = FontWeight.Bold, fontSize = 25.sp)
            Text("BSc (H) Computer Science , 4012")
        }
    }
}

data class Queries(val registrationId : Int, val date : String, val status : String)

@Composable
fun List_of_queries(data : List<Queries>) {

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        items(data){
            item ->
            Card (modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(6.dp),
                shape = RoundedCornerShape(7.dp),
                
            ) {
                Row(modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Reg no.",fontWeight = FontWeight.Bold,fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = "${item.registrationId}",fontSize = 18.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Date",fontWeight = FontWeight.Bold,fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = item.date,fontSize = 18.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Status",fontWeight = FontWeight.Bold,fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(text = item.status,fontSize = 18.sp, color = colorResource(R.color.green), fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}

