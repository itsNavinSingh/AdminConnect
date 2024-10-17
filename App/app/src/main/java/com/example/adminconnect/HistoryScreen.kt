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
fun HistoryTab() {

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp)) {
            Text("History", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        }

        val completedQueries = listOf(
            CQueries(275, "12/3/2024", "Completed", colorResource(R.color.green)),
            CQueries(157, "11/4/2024", "Rejected", Color.Red),
            CQueries(160, "21/3/2024", "Rejected", Color.Red)

        )

        Completed_queries(data = completedQueries)
    }
}

data class CQueries(val registrationId : Int, val date : String, val status : String, val clr : Color)

@Composable
fun Completed_queries(data : List<CQueries>) {

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
                        Text(text = item.status,fontSize = 18.sp, fontWeight = FontWeight.SemiBold , color = item.clr)
                    }
                }
            }
        }
    }
}