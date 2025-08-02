package com.example.heartdiseasepredictionandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel

@Composable
fun ModelSelectionScreen(navController: NavController, predictionViewModel: PredictionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select Prediction Model",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                predictionViewModel.setModelType("Cluster")
                navController.navigate("prediction_input")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cluster Model")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                predictionViewModel.setModelType("Neural Network")
                navController.navigate("prediction_input")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Neural Network Model")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Diagnose")
        }
    }
}