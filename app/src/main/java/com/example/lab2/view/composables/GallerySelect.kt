package com.example.lab2.view.composables

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import com.example.lab2.util.PermissionForGallery
import com.google.accompanist.permissions.ExperimentalPermissionsApi

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

@ExperimentalPermissionsApi
@Composable
fun GallerySelect(
    onImageUri: (Uri) -> Unit = { }
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageUri(uri ?: EMPTY_IMAGE_URI)
        }
    )

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch("image/*")
        }
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        PermissionForGallery(
            permission = Manifest.permission.ACCESS_MEDIA_LOCATION,
            rationale = "For your privacy, we are asking for permission.",
            permissionNotAvailableContent = {
                LaunchGallery()
            },
        ) {
            LaunchGallery()
        }
    } else {
        LaunchGallery()
    }
}