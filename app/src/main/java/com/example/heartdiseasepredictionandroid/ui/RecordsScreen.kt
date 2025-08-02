package com.example.heartdiseasepredictionandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.heartdiseasepredictionandroid.model.Record
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun RecordsScreen(navController: NavController, predictionViewModel: PredictionViewModel) {
    val recordsResult by predictionViewModel.records.collectAsState()
    val errorMessage by predictionViewModel.errorMessage.collectAsState()
    val isLoading by predictionViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        predictionViewModel.fetchAllRecords()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "All Patient Records",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "An error occurred while fetching records",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
            recordsResult == null -> {
                Text(
                    text = "No records available",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                recordsResult?.fold(
                    onSuccess = { records ->
                        if (records.isEmpty()) {
                            Text(
                                text = "No patient records found",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f)
                            ) {
                                itemsIndexed(records) { index, record ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Text(
                                                text = "Patient ${index + 1}",
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Text(
                                                text = "Username: ${record.username}",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = "Model: ${record.model_type}",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            Text(
                                                text = "Timestamp: ${record.timestamp}",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                            val predictionData = try {
                                                Json.parseToJsonElement(record.prediction_data).jsonObject
                                            } catch (e: Exception) {
                                                null
                                            }
                                            Text(
                                                text = "Prediction: ${
                                                    predictionData?.let {
                                                        val cluster = it["cluster"]?.jsonPrimitive?.content ?: "Unknown"
                                                        val riskLevel = it["risk_level"]?.jsonPrimitive?.content ?: "Unknown"
                                                        "Cluster $cluster ($riskLevel)"
                                                    } ?: "Invalid prediction data"
                                                }",
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                    onFailure = { e ->
                        Text(
                            text = "Failed to load records: ${e.message}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Back")
        }
    }
}