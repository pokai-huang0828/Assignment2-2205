package com.example.lab2.view.screens

import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lab2.R
import com.example.lab2.auth.Auth
import com.example.lab2.view.navigation.Route


@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@Composable
fun SignUpScreen(
    navController: NavController,
    auth: Auth
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }


    LogoTextBox()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(top = 150.dp))
        Text(
            text = "SIGN UP",
            color = Color.Black,
            fontSize = 35.sp,
            textAlign = TextAlign.Center,
        )

        //Email input field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = {
                Text("Email address") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email",
                    tint = Color.Black,
                )
            },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = ThirdColor,
//                focusedLabelColor = SecondaryColor,
//                unfocusedBorderColor = SecondaryColor
//            )
        )

        //Password input field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                Text("Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Password",
                    tint = Color.Black,
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    if (passwordVisibility)
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_visibility_off_black_24dp),
                            contentDescription = "",
                            modifier = Modifier.size(25.dp)
                        )
                    else Icon(painter = painterResource(id = R.drawable.baseline_visibility_black_24dp),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                }
            },
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = ThirdColor,
//                focusedLabelColor = SecondaryColor,
//                unfocusedBorderColor = SecondaryColor
//            )
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                Text("Confirm Password") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirm Password",
                    tint = Color.Black,
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    confirmPasswordVisibility = !confirmPasswordVisibility
                }) {
                    if (confirmPasswordVisibility)
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_visibility_off_black_24dp),
                            contentDescription = "",
                            modifier = Modifier.size(25.dp)
                        )
                    else Icon(painter = painterResource(id = R.drawable.baseline_visibility_black_24dp),
                        contentDescription = "",
                        modifier = Modifier.size(25.dp)
                    )
                }
            },

//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = ThirdColor,
//                focusedLabelColor = SecondaryColor,
//                unfocusedBorderColor = SecondaryColor
//            ),
        )
        Text(
            text = if( password != confirmPassword )"Your password is not match, please check again" else "",
            color = Color.Red
        )

        val selected = remember { mutableStateOf(false) }
        val scale = animateFloatAsState(if (selected.value) 0.95f else 1f)
        Button(
            onClick = { auth.signUpWithEmailAndPassword(navController, email, password) },

            modifier = Modifier
                .scale(scale.value)
                .padding(top = 15.dp)
                .fillMaxWidth(0.75f)
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(25.dp), clip = true)
                .height(50.dp)
                .pointerInteropFilter {
                    when (it.action) {
                        MotionEvent.ACTION_DOWN -> {
                            selected.value = true
                        }
                        MotionEvent.ACTION_UP -> {
                            selected.value = false
                        }
                    }
                    true
                }
        ) {
            Text(

                color = Color.White,
                text = "Sign Up",
                fontSize = 16.sp,
                fontWeight = FontWeight.W900,
            )
        }


        Spacer(modifier = Modifier.padding(top = 25.dp))

        Text(
            text =
            buildAnnotatedString {
                append("Have an account? Back to Login!")
                addStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline
                    ),
                    start = 17,
                    end = 31
                )
            },
            modifier = Modifier.clickable { navController.navigate(Route.SignInScreen.route) }
        )
    }
}