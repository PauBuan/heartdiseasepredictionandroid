package com.example.heartdiseasepredictionandroid.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.viewmodel.AuthViewModel
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel

@Composable
fun Navigation(navController: NavHostController, authViewModel: AuthViewModel, predictionViewModel: PredictionViewModel) {
    NavHost(navController = navController, startDestination = "landing") {
        composable("landing") {
            LandingScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController, authViewModel)
        }
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("main") {
            MainScreen(navController, predictionViewModel)
        }
        composable("diagnose") {
            DiagnoseScreen(navController, predictionViewModel)
        }
        composable("model_selection") {
            ModelSelectionScreen(navController, predictionViewModel)
        }
        composable("prediction_input") {
            PredictionInputScreen(navController, authViewModel, predictionViewModel)
        }
        composable("prediction_result/{result}") { backStackEntry ->
            val resultJson = backStackEntry.arguments?.getString("result") ?: ""
            PredictionResultScreen(navController, predictionViewModel, resultJson)
        }
        composable("records") {
            RecordsScreen(navController, predictionViewModel)
        }
    }
}