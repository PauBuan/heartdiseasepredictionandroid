package com.example.heartdiseasepredictionandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.heartdiseasepredictionandroid.ui.Navigation
import com.example.heartdiseasepredictionandroid.ui.theme.HeartDiseasePredictionAndroidTheme
import com.example.heartdiseasepredictionandroid.viewmodel.AuthViewModel
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val predictionViewModel: PredictionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HeartDiseasePredictionAndroidTheme {
                AppNavigation(authViewModel, predictionViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(authViewModel: AuthViewModel, predictionViewModel: PredictionViewModel) {
    val navController = rememberNavController()
    Navigation(navController = navController, authViewModel = authViewModel, predictionViewModel = predictionViewModel)
}