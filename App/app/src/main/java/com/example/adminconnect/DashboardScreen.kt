package com.example.adminconnect

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import com.example.adminconnect.OngoingTab
import com.example.adminconnect.HistoryTab
import com.example.adminconnect.CreateTab



@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {

    val pagerState = rememberPagerState(pageCount = { 3 }) // Initialize pagerState here
//    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

//            DashboardBottomBar(navController = navController)

            DashboardBottomBar(pagerState = pagerState) // Pass pagerState to BottomBar
        }
    ) {  innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Profile_info()

//            NavHost(
//                navController = navController,
//                startDestination = "ongoing",
//                modifier = Modifier.fillMaxSize()
//            ) {
//                composable("ongoing") { OngoingTab() }
//                composable("history") { HistoryTab() }
//                composable("create") { CreateTab() }
//            }

            SwipeNavigationApp(pagerState = pagerState)  // Use SwipeNavigationApp with pagerState
        }
    }
}


//@OptIn(ExperimentalPagerApi::class)    // here we will have a tab row to display current screen but not able to navigate through bottom incons
//@Composable
//fun SwipeNavigationApp() {
//    val pagerState = rememberPagerState(pageCount = { 3 })
//    val coroutineScope = rememberCoroutineScope()
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        // Tab Row to Display the Screen Titles
//        TabRow(selectedTabIndex = pagerState.currentPage) {
//            listOf("Ongoing", "History", "Create").forEachIndexed { index, title ->
//                Tab(
//                    text = { Text(text = title) },
//                    selected = pagerState.currentPage == index,
//                    onClick = {
//                        coroutineScope.launch {
//                            pagerState.animateScrollToPage(index)
//                        }
//                    }
//                )
//            }
//        }
//
//        // Horizontal Pager to Swipe Between Screens
//        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
//            when (page) {
//                0 -> OngoingTab()
//                1 -> HistoryTab()
//                2 -> CreateTab()
//            }
//        }
//    }
//}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SwipeNavigationApp(pagerState: PagerState) {
    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
        when (page) {
            0 -> OngoingTab()
            1 -> HistoryTab()
            2 -> CreateTab()
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


//@Composable
//fun DashboardBottomBar(navController : NavHostController) {
//    BottomAppBar(
//        content = {
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                BottomAppBarIcon(
//                    icon = Icons.Filled.Send,
//                    onClick = { navController.navigate("ongoing") },
//                    label = "Ongoing"
//                )
//                BottomAppBarIcon(
//                    icon = Icons.Filled.Refresh,
//                    onClick = { navController.navigate("history") },
//                    label = "History"
//                )
//                BottomAppBarIcon(
//                    icon = Icons.Filled.Add,
//                    onClick = { navController.navigate("create") },
//                    label = "Create"
//                )
//            }
//        }
//    )
//}

@Composable
fun DashboardBottomBar(pagerState: PagerState) {
    val coroutineScope = rememberCoroutineScope()

    BottomAppBar(
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BottomAppBarIcon(
                    icon = Icons.Filled.Send,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(0) }
                    },
                    label = "Ongoing"
                )
                BottomAppBarIcon(
                    icon = Icons.Filled.Refresh,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(1) }
                    },
                    label = "History"
                )
                BottomAppBarIcon(
                    icon = Icons.Filled.Add,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(2) }
                    },
                    label = "Create"
                )
            }
        }
    )
}


//@Composable
//fun Profile_info() {
//
//    Row(modifier = Modifier
//        .fillMaxWidth()
//        .padding(20.dp),
//        verticalAlignment = Alignment.CenterVertically) {
//        Surface(
//            modifier = Modifier
//                .size(70.dp)
//                .padding(10.dp),
//            shape = CircleShape,
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.profileimg),
//                contentDescription = "profile image",
//            )
//        }
//        Column {
//            Text("Avinash Madkatte", fontWeight = FontWeight.Bold, fontSize = 25.sp)
//            Text("BSc (H) Computer Science , 4012")
//        }
//    }
//}

@Composable
fun Profile_info() {

    val expanded = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .animateContentSize()
//            .height(if (expanded.value) 300.dp else 120.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded.value = !expanded.value
            }
    ) {
        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                Column() {
                    Text("Avinash Madkatte", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Text("BSc (H) Computer Science , 4012")

                }
            }
            if (expanded.value) {
                Column(
                    modifier = Modifier.padding(start=90.dp).fillMaxWidth(),
                    ) {

                    Row {
                        Text("ID - ", fontWeight = FontWeight.Bold)
                        Text("avinash.cs4012@rla.du.ac.in")
                    }
                    Row {
                        Text("Exam Roll no. - ", fontWeight = FontWeight.Bold)
                        Text("22058570012")
                    }
                    Row {
                        Text("Sem - ", fontWeight = FontWeight.Bold)
                        Text("5 , 2022")
                    }
                    Row {
                        Text("Ph. no. - ", fontWeight = FontWeight.Bold)
                        Text("8744077507")
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {}) { Text("Log Out")}
                    Spacer(modifier = Modifier.height(20.dp))
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

