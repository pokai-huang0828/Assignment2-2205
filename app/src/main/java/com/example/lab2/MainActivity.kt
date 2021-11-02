package com.example.lab2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.lab2.auth.Auth
import com.example.lab2.ui.theme.Lab2Theme
import com.example.lab2.util.Permission
import com.example.lab2.view.navigation.Navigation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.*

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var auth: Auth
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var  locationCallback: LocationCallback
    private var requestingLocationUpdates = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Auth(this, getString(R.string.default_web_client_id))

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                for (location in locationResult.locations){
                    Log.d(TAG, location.toString())
                }
            }
        }
        setContent {
            Lab2Theme {
                val context = LocalContext.current
                Permission(
                    permission = Manifest.permission.ACCESS_FINE_LOCATION,
                    rationale = "Greetings, to give your better services, please check the permission for location.",
                    permissionNotAvailableContent = {
                        Column() {
                            Text("Thank you")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                context.startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                })
                            }) {
                                Text("Open Settings")
                            }
                        }
                    }
                ){
                    Navigation(auth)
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (requestingLocationUpdates){
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()

        stopLocationUpdates()
    }

    private fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.create().apply {
            interval = 5000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        return locationRequest
    }

    private fun startLocationUpdates() {
        val locationRequest = createLocationRequest()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @ExperimentalAnimationApi
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_AUTH) {
            auth.onGoogleSignInResult(data)
        }
    }

    companion object {
        const val GOOGLE_AUTH = 9001
        val TAG = "From Lecture 5"
    }
}
