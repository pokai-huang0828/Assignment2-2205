package com.example.lab2.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab2.auth.Auth
import com.example.lab2.view.screens.MainScreen
import com.example.lab2.view.screens.SignInScreen
import com.example.lab2.view.screens.SignUpScreen
import com.example.lab2.view.screens.SplashScreenAnimate
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun Navigation(auth: Auth) {
    val navController = rememberNavController()

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
    }
}