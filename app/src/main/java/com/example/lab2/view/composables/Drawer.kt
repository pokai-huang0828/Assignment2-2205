package com.example.lab2.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab2.R
import com.example.lab2.auth.Auth
import com.example.lab2.view.navigation.Route

@Composable
fun Drawer(
    navController: NavController,
    auth: Auth
    ) {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lab2logotext),
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                    )
                Text(
                    text = "Welcome User",
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }
        }

        Divider(color = LightGray, thickness = 1.dp)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),

        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { navController.navigate(Route.MainScreen.route) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
                ) {
                Spacer(modifier = Modifier.padding(10.dp))
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "",
                    tint = Black
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Home",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { navController.navigate(Route.ProfileScreen.route) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.padding(10.dp))
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "",
                    tint = Black
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Profile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clickable { auth.signOut(navController) },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.padding(10.dp))
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "",
                    tint = Black
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = "Logout",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Black
                )
            }
        }
    }
}