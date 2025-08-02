package com.example.heartdiseasepredictionandroid.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel

@Composable
fun MainScreen(navController: NavHostController, predictionViewModel: PredictionViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Heart Disease Prediction", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("diagnose") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Diagnose")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.navigate("records") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Records")
        }
    }
}