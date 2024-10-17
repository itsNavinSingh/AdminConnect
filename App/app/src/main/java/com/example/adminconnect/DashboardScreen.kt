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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            DashboardBottomBar(navController = navController)
        }
    ) {  innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Profile_info()


            NavHost(
                navController = navController,
                startDestination = "ongoing",
                modifier = Modifier.fillMaxSize()
            ) {
                composable("ongoing") { OngoingTab() }
                composable("history") { HistoryTab() }
                composable("create") { CreateTab() }
            }
//            OngoingTab()

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
fun DashboardBottomBar(navController : NavHostController) {
    BottomAppBar(
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BottomAppBarIcon(
                    icon = Icons.Filled.Send,
                    onClick = { navController.navigate("ongoing") },
                    label = "Ongoing"
                )
                BottomAppBarIcon(
                    icon = Icons.Filled.Refresh,
                    onClick = { navController.navigate("history") },
                    label = "History"
                )
                BottomAppBarIcon(
                    icon = Icons.Filled.Add,
                    onClick = { navController.navigate("create") },
                    label = "Create"
                )
            }
        }
    )
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


@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
}

