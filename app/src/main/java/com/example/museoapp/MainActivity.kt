package com.example.museoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.museoapp.ui.MuseoApp
import com.example.museoapp.ui.theme.MuseoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MuseoAppTheme {
                MuseoApp()
            }
        }
    }
}


