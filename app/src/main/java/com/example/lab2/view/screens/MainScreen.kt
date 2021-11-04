package com.example.lab2.view.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lab2.auth.Auth
import com.example.lab2.view.composables.Drawer
import com.example.lab2.view.composables.MapBox
import com.example.lab2.view.composables.TopBar

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
            scaffoldState = scaffoldState) },
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


