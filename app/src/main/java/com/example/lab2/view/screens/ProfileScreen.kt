package com.example.lab2.view.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.view.MotionEvent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.lab2.R
import com.example.lab2.auth.Auth
import com.example.lab2.model.entities.User
import com.example.lab2.model.repositories.FireStorageRepo
import com.example.lab2.model.repositories.UserRepository
import com.example.lab2.model.responses.Resource
import com.example.lab2.view.composables.Drawer
import com.example.lab2.view.composables.EMPTY_IMAGE_URI
import com.example.lab2.view.composables.GallerySelect
import com.example.lab2.view.composables.TopBar
import com.example.lab2.view.navigation.Route
import com.example.lab2.viewmodels.UserViewModel
import com.example.lab2.viewmodels.UserViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@Composable
fun ProfileScreen(
    navController: NavController,
                  auth: Auth
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    Scaffold(
        topBar = { TopBar(
            scope = scope,
            scaffoldState = scaffoldState)
                 },
        scaffoldState = scaffoldState,
        drawerContent = {
            Drawer(navController = navController, auth = auth)
        },
        content = { UserProfile(navController = navController, auth = auth) }
    )
}

@ExperimentalComposeUiApi
@SuppressLint("ProduceStateDoesNotAssignValue")
@ExperimentalPermissionsApi
@Composable
fun UserProfile(
    navController: NavController,
    auth: Auth,
    userVM: UserViewModel = viewModel(factory = UserViewModelFactory(UserRepository()))
) {
        var id by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var thumbnailUrl by remember { mutableStateOf("") }

        // Set up for picking image from gallery
        val context = LocalContext.current
        var coroutineScope = rememberCoroutineScope()
        var fireStorageRepo by remember { mutableStateOf(FireStorageRepo()) }
        var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
        var imageChanged by remember { mutableStateOf(false) }
        var showGallery by remember { mutableStateOf(false) }

        // Get User Info from firebase
        produceState(initialValue = false) {
            val resource = userVM.getUserById((auth.currentUser as FirebaseUser).uid)

            if (resource is Resource.Success<*>) {
                val user = resource.data as User
                id = user.id
                name = user.name
                email = user.email
                thumbnailUrl = user.thumbnailUrl
            }

            if (thumbnailUrl.isNotEmpty()) {
                imageUri = Uri.parse(thumbnailUrl)
            }
        }
        // Show Gallery Screen or Profile Screen
        if (showGallery) {
            GallerySelect { uri ->
                showGallery = false
                imageUri = uri
                imageChanged = true
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp)
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    Box(contentAlignment = Alignment.TopEnd) {

                        Image(
                            painter =
                            if (thumbnailUrl.isEmpty() && imageUri === EMPTY_IMAGE_URI)
                                painterResource(R.drawable.default_image)
                            else
                                rememberImagePainter(imageUri),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                        )

                        OutlinedButton(
                            onClick = {
                                showGallery = true
                            },
                            modifier = Modifier
                                .size(30.dp)
                                .offset(y = (117).dp, x = (-5).dp),
                            shape = CircleShape,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "",
                                tint = Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                //Inputs
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {

                    //Name
                    TextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier
                            .fillMaxWidth(0.9f),
                        maxLines = 1,
                        label = {
                            Text(
                                text = "Name",
                                fontSize = 20.sp,
                                color = Black,
                                fontWeight = FontWeight.W900,
                            )
                        },

                        )

                    // Email
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(vertical = 10.dp),
                        label = {
                            Text(
                                text = "Email",
                                fontSize = 20.sp,
                                color = Black,
                                fontWeight = FontWeight.W900,
                            )
                        },
                        maxLines = 1,
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(5.dp))

                    // update button
                    val selected = remember { mutableStateOf(false) }
                    val scale = animateFloatAsState(if (selected.value) 0.95f else 1f)
                    Button(
                        onClick = {  },

                        modifier = Modifier
                            .scale(scale.value)
                            .padding(top = 1.dp)
                            .fillMaxWidth(0.75f)
                            .height(50.dp)
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(25.dp), clip = true)
                            .pointerInteropFilter {
                                when (it.action) {
                                    MotionEvent.ACTION_DOWN -> {
                                        selected.value = true
                                        // upload image to fire storage if user has provided one
                                        if (imageChanged) {

                                            var imageDownloadUrl: String? = null

                                            coroutineScope.launch {
                                                fireStorageRepo.uploadImageToStorage(
                                                    context,
                                                    imageUri,
                                                    imageUri.lastPathSegment!!
                                                ) {
                                                    imageDownloadUrl = it.toString()
                                                }.join()

                                            }.invokeOnCompletion {
                                                userVM.updateUser(
                                                    User(
                                                        id = id,
                                                        name = name,
                                                        email = email,
                                                        thumbnailUrl = imageDownloadUrl ?: "",
                                                    )
                                                ) {
                                                    // if resource is success
                                                    if (it is Resource.Success) {
                                                        navController.navigate(Route.MainScreen.route) {
                                                            popUpTo(Route.MainScreen.route)
                                                        }
                                                    }
                                                }
                                            }

                                        } else {
                                            // If user did not change image
                                            userVM.updateUser(
                                                User(
                                                    id = id,
                                                    name = name,
                                                    email = email,
                                                    thumbnailUrl = thumbnailUrl,
                                                )
                                            ) {
                                                // If resource is success
                                                if (it is Resource.Success) {
                                                    navController.navigate(Route.MainScreen.route) {
                                                        popUpTo(Route.MainScreen.route)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    MotionEvent.ACTION_UP -> {
                                        selected.value = false
                                    }
                                }
                                true
                            },
                        contentPadding = PaddingValues(5.dp)
                    ) {
                        Text(
                            text = "Update",
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W900
                        )
                    }
                }
            }
        }
}
