package com.example.lab2.view.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lab2.auth.Auth
import com.example.lab2.view.composables.Drawer
import com.example.lab2.view.composables.MapBox
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun MainScreen(navController: NavController, auth: Auth) {

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    Scaffold(
        modifier = Modifier
            .background(color = Color.Gray)
            .fillMaxSize(),
        topBar = { TopBar(
                scope = scope,
                scaffoldState = scaffoldState,
                navController = navController,
                auth = auth) },
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer(navController = navController, auth = auth)
        },


    ) {

        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        
                ){
            
            MapBox(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                ,
            ){

            }
        }
    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController, auth: Auth) {
    var visible by remember { mutableStateOf(false) }
    Column(Modifier.shadow(elevation = 5.dp)) {
        Row(
            modifier = Modifier
                .background(Black)
                .fillMaxWidth()
                .height(60.dp)
                .padding(vertical = 3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "",
                    tint = White
                )
            }

            Text(
                text = "Main page",
                color = White,
                fontSize = 30.sp,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { }
            )

            Row(
                modifier = Modifier
                    .background(Black)
                    .height(60.dp)
                    .padding(2.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { visible = !visible },
                    modifier = Modifier
                        .padding(5.dp)
                        .size(45.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = White,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

//                IconButton(
//                    onClick = { auth.signOut(navController) },
//                    modifier = Modifier
//                        .padding(5.dp)
//                        .size(45.dp)
//                        .clip(CircleShape)
//                ) {
//                    Icon(
//                        imageVector = Icons.Filled.ExitToApp,
//                        contentDescription = "Logout",
//                        tint = White,
//                        modifier = Modifier
//                            .size(28.dp)
//                    )
//                }
            }
        }
        AnimatedVisibility(visible) {
            SearchBar()
        }
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Black,
        elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(8.dp)
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                maxLines = 1,
                label = {
                    Text(text = "Search")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = White,
                        modifier = Modifier.size(30.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search,
                ),
            )
        }
    }
}