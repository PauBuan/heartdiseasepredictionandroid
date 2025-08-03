package com.example.heartdiseasepredictionandroid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel
import kotlinx.serialization.json.Json

@Composable
fun PredictionResultScreen(
    navController: NavHostController,
    predictionViewModel: PredictionViewModel,
    resultJson: String
) {
    val result = Json.decodeFromString<PredictionResult>(resultJson)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Prediction Result", style = MaterialTheme.typography.headlineMedium)

        // Prediction Details Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Prediction Details", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Risk Level: ${result.risk_level}")
                Text("Cluster: ${result.cluster}")
                Text("Model: ${result.model_type}")
                Text("Message: ${result.message}")
            }
        }

        // Input Data Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Input Data Used:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                result.input.forEach { (key, value) ->
                    Text(text = "$key: $value")
                }
            }
        }

        // Risk Level Guide Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Risk Level Guide", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                RiskDescription(
                    title = "Low Risk (Cluster 1)",
                    color = MaterialTheme.colorScheme.primary,
                    description = "Indicates minimal likelihood of heart disease. Patients typically have normal cholesterol levels, healthy heart rates, and no significant symptoms. Regular check-ups are recommended."
                )

                RiskDescription(
                    title = "Moderate Risk (Cluster 2)",
                    color = MaterialTheme.colorScheme.tertiary,
                    description = "Suggests a moderate chance of heart disease. This cluster may include patients with elevated cholesterol or mild symptoms. Lifestyle changes and monitoring are advised."
                )

                RiskDescription(
                    title = "High Risk (Cluster 3)",
                    color = MaterialTheme.colorScheme.error,
                    description = "Indicates a high likelihood of heart disease. Patients often have high cholesterol, abnormal heart rates, or multiple risk factors. Immediate medical consultation is recommended."
                )
            }
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

@Composable
fun RiskDescription(title: String, color: androidx.compose.ui.graphics.Color, description: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            color = color,
            style = MaterialTheme.typography.titleSmall
        )
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}