package com.example.lab2.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .background(color = Color.Gray)
            .fillMaxSize()
    ) {
        var searchText by remember { mutableStateOf("") }
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = Color.Gray,
            elevation = 5.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(8.dp)
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    maxLines = 1,
                    label = {
                        Text(text = "Search")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                )
            }
        }
    }
}