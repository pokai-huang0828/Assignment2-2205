package com.example.lab2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.example.lab2.auth.Auth
import com.example.lab2.ui.theme.Lab2Theme
import com.example.lab2.view.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth





@ExperimentalComposeUiApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private lateinit var auth: Auth
    private val mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Auth(this, getString(R.string.default_web_client_id))
        setContent {
            Lab2Theme {
                Navigation(auth)
            }
        }
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
    }


}
