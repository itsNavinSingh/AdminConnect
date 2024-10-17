package com.example.adminconnect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OngoingTab() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 50.dp)) {
        Text("Ongoing", fontWeight = FontWeight.Bold, fontSize = 30.sp)
    }

    val ongoingQueries = listOf(
        Queries(275, "12/3/2024", "Received", colorResource(R.color.green)),
        Queries(157, "11/4/2024", "Received", colorResource(R.color.green)),
        Queries(160, "21/3/2024", "Ongoing", Color.Gray),
        Queries(364, "10/2/2024", "Received", colorResource(R.color.green)),
        Queries(732, "09/3/2024", "Ongoing", Color.Gray),
        Queries(178, "08/1/2024", "Ongoing", Color.Gray),
        Queries(679, "12/3/2024", "Received", colorResource(R.color.green)),
        Queries(243, "23/2/2024", "Ongoing", Color.Gray),
        Queries(689, "31/2/2024", "Ongoing", Color.Gray)
    )

    List_of_queries(data = ongoingQueries)
}

data class Queries(val registrationId : Int, val date : String, val status : String, val clr : Color)

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
                        Text(text = item.status,fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = item.clr)
                    }
                }
            }
        }
    }
}