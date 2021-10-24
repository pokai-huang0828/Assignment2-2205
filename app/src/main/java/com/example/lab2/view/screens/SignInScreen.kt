package com.example.lab2.view.screens

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab2.R
import com.example.lab2.view.navigation.Route


@ExperimentalComposeUiApi
@Composable
fun SignInScreen(navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    LogoTextBox()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(top = 100.dp))
        Text(
            text = "Welcome To IK Lab2",
            color = Color.Black,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
        )

        //Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text("Email address")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email",
                    tint = Color.Black,
                )
            },
        )

        //Password input field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                Text("Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = Color.Black,
                )
            },
            trailingIcon = {
                val image = if (passwordVisibility)
                    Icons.Filled.Close
                else Icons.Filled.Face

                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(imageVector = image, "")
                }
            },
        )

        val selected = remember { mutableStateOf(false) }
        val scale = animateFloatAsState(if (selected.value) 0.8f else 1f)
        Button(
            onClick = {  },

            modifier = Modifier
                .scale(scale.value)
                .padding(top = 15.dp)
                .fillMaxWidth(0.8f)
                .height(50.dp)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(20.dp), clip = true)
                .pointerInteropFilter {
                                      when(it.action){
                                          MotionEvent.ACTION_DOWN -> {
                                              selected.value = true }
                                          MotionEvent.ACTION_UP -> {
                                              selected.value = false }
                                      }
                    true
                },

            ) {
            Text(
                color = Color.White,
                text = "Sign In",
                fontSize = 16.sp,
                fontWeight = FontWeight.W900,
            )
        }

        Spacer(modifier = Modifier.padding(top = 25.dp))

        Text(
            text =
            buildAnnotatedString {
                append("Don't have an account? Sign up here!")
                addStyle(
                    style = SpanStyle(

                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    ),
                    start = 23,
                    end = 36
                )
            },
            modifier = Modifier.clickable { navController.navigate(Route.SignUpScreen.route) }
        )
    }
}

@Composable
fun LogoTextBox() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(270.dp)
            .fillMaxWidth()
            .background(Color.Gray),
    ) {
        Image(
            painter = painterResource(id = R.drawable.lab2logotext),
            contentDescription = "logo",
            modifier = Modifier
                .size(180.dp),
        )

    }
}