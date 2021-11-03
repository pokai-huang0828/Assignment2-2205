package com.example.lab2.view.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.lab2.R

sealed class Route(val route: String, @StringRes val resourceID: Int, val icon: ImageVector?, var title: String){
    object SplashScreen : Route("splashScreen", R.string.splash_screen, null, "Splash Screen")
    object MainScreen : Route("mainScreen", R.string.main_screen, Icons.Filled.Home, "Home")
    object SignInScreen : Route("signInScreen", R.string.sign_in_screen, null, "Logout")
    object SignUpScreen : Route("signUpScreen", R.string.sign_up_screen, null, "")
    object ProfileScreen : Route("profileScreen", R.string.profile_screen, Icons.Filled.AccountCircle, "Profile")

}