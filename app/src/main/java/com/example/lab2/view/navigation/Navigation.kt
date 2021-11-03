package com.example.lab2.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab2.auth.Auth
import com.example.lab2.view.screens.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun Navigation(auth: Auth) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen.route
    ){
        composable(Route.SplashScreen.route){
            SplashScreenAnimate(navController = navController, auth = auth)
        }

        composable(Route.MainScreen.route){
            MainScreen(navController = navController, auth = auth)
        }

        composable(Route.SignInScreen.route){
            SignInScreen(navController = navController, auth = auth)
        }

        composable(Route.SignUpScreen.route){
            SignUpScreen(navController = navController, auth = auth)
        }

        composable(Route.ProfileScreen.route){
            ProfileScreen(navController = navController, auth = auth)
        }
    }
}