package com.example.lab2.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab2.view.screens.MainScreen
import com.example.lab2.view.screens.SignInScreen
import com.example.lab2.view.screens.SignUpScreen
import com.example.lab2.view.screens.SplashScreenAnimate

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun Navigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Route.SplashScreen.route
    ){
        composable(Route.SplashScreen.route){
            SplashScreenAnimate(navController = navController)
        }

        composable(Route.MainScreen.route){
            MainScreen(navController = navController)
        }

        composable(Route.SignInScreen.route){
            SignInScreen(navController = navController)
        }

        composable(Route.SignUpScreen.route){
            SignUpScreen()
        }
    }
}