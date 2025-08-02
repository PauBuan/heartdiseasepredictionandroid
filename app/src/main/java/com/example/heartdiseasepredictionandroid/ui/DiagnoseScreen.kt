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
fun DiagnoseScreen(navController: NavController, predictionViewModel: PredictionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Diagnose Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("model_selection") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Select Model for Diagnosis")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("records") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View All Records")
        }
    }
}