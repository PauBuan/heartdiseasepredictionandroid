package com.example.heartdiseasepredictionandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel
import kotlinx.serialization.json.Json

@Composable
fun PredictionResultScreen(navController: NavHostController, predictionViewModel: PredictionViewModel, resultJson: String) {
    val result = Json.decodeFromString<PredictionResult>(resultJson)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Prediction Result", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Risk Level: ${result.risk_level}")
        Text(text = "Cluster: ${result.cluster}")
        Text(text = "Model: ${result.model_type}")
        Text(text = "Message: ${result.message}")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Input Data:", style = MaterialTheme.typography.titleMedium)
        result.input.forEach { (key, value) ->
            Text(text = "$key: $value")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("main") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Main")
        }
    }
}