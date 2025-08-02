package com.example.heartdiseasepredictionandroid.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.viewmodel.AuthViewModel
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



@Composable
fun PredictionInputScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    predictionViewModel: PredictionViewModel
) {
    val user by authViewModel.user.collectAsState()
    val modelType by predictionViewModel.modelType.collectAsState()
    val predictionResult by predictionViewModel.predictionResult.collectAsState()
    val isLoading by predictionViewModel.isLoading.collectAsState()
    val errorMessage by predictionViewModel.errorMessage.collectAsState()

    var age by remember { mutableStateOf("") }
    var trestbps by remember { mutableStateOf("") }
    var chol by remember { mutableStateOf("") }
    var thalach by remember { mutableStateOf("") }
    var oldpeak by remember { mutableStateOf("") }
    var ca by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }
    var exang by remember { mutableStateOf("") }
    var slope by remember { mutableStateOf("") }
    var thal by remember { mutableStateOf("") }

    // Validation states
    var ageError by remember { mutableStateOf<String?>(null) }
    var trestbpsError by remember { mutableStateOf<String?>(null) }
    var cholError by remember { mutableStateOf<String?>(null) }
    var thalachError by remember { mutableStateOf<String?>(null) }
    var oldpeakError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(predictionResult) {
        predictionResult?.let { result ->
            result.fold(
                onSuccess = { prediction ->
                    val resultJson = Json.encodeToString(prediction)
                    navController.navigate("prediction_result/$resultJson")
                    predictionViewModel.clearPredictionResult()
                },
                onFailure = { /* Error handled by errorMessage */ }
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Hello, Doctor ${user?.name ?: "Guest"}, a ${user?.specialty ?: "Specialist"}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Enter Patient Data (${modelType ?: "Unknown"})",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Age
                    OutlinedTextField(
                        value = age,
                        onValueChange = {
                            age = it
                            ageError = validateNumber(it, 25f, 100f, "Age")
                        },
                        label = { Text("Age") },
                        isError = ageError != null,
                        supportingText = { Text(ageError ?: "Range: 25-100 years") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Sex
                    DropdownField(
                        label = "Sex",
                        value = sex,
                        options = listOf("Male" to "1", "Female" to "0"),
                        onValueChange = { sex = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Chest Pain Type
                    DropdownField(
                        label = "Chest Pain Type",
                        value = cp,
                        options = listOf(
                            "Typical Angina" to "0",
                            "Atypical Angina" to "1",
                            "Non-Anginal Pain" to "2",
                            "Asymptomatic" to "3"
                        ),
                        onValueChange = { cp = it },
                        supportingText = "0-3 (select type)",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Cholesterol
                    OutlinedTextField(
                        value = chol,
                        onValueChange = {
                            chol = it
                            cholError = validateNumber(it, 100f, 500f, "Cholesterol")
                        },
                        label = { Text("Cholesterol") },
                        isError = cholError != null,
                        supportingText = { Text(cholError ?: "Range: 100-500 mg/dL") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Max Heart Rate
                    OutlinedTextField(
                        value = thalach,
                        onValueChange = {
                            thalach = it
                            thalachError = validateNumber(it, 60f, 200f, "Max Heart Rate")
                        },
                        label = { Text("Max Heart Rate") },
                        isError = thalachError != null,
                        supportingText = { Text(thalachError ?: "Range: 60-200 bpm") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Oldpeak
                    OutlinedTextField(
                        value = oldpeak,
                        onValueChange = {
                            oldpeak = it
                            oldpeakError = validateNumber(it, 1f, 10f, "Oldpeak")
                        },
                        label = { Text("Oldpeak (ST Depression)") },
                        isError = oldpeakError != null,
                        supportingText = { Text(oldpeakError ?: "Range: 1-10") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Number of Major Vessels
                    DropdownField(
                        label = "Number of Major Vessels",
                        value = ca,
                        options = listOf("0" to "0", "1" to "1", "2" to "2", "3" to "3"),
                        onValueChange = { ca = it },
                        supportingText = "0-3 (count of vessels)",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Thalassemia
                    DropdownField(
                        label = "Thalassemia",
                        value = thal,
                        options = listOf(
                            "Normal" to "0",
                            "Fixed Defect" to "1",
                            "Reversible Defect" to "2"
                        ),
                        onValueChange = { thal = it },
                        supportingText = "0 = Normal, 1 = Fixed Defect, 2 = Reversible Defect",
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Neural Network-specific fields
                    if (modelType == "Neural Network") {
                        // Resting Blood Pressure
                        OutlinedTextField(
                            value = trestbps,
                            onValueChange = {
                                trestbps = it
                                trestbpsError = validateNumber(it, 90f, 200f, "Resting Blood Pressure")
                            },
                            label = { Text("Resting Blood Pressure") },
                            isError = trestbpsError != null,
                            supportingText = { Text(trestbpsError ?: "Range: 90-200 mmHg") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Exercise-Induced Angina
                        DropdownField(
                            label = "Exercise-Induced Angina",
                            value = exang,
                            options = listOf("No" to "0", "Yes" to "1"),
                            onValueChange = { exang = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Slope of Peak Exercise ST Segment
                        DropdownField(
                            label = "Slope of Peak Exercise ST Segment",
                            value = slope,
                            options = listOf(
                                "Upsloping" to "0",
                                "Flat" to "1",
                                "Downsloping" to "2"
                            ),
                            onValueChange = { slope = it },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Error message
                    errorMessage?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // Submit Button
                    Button(
                        onClick = {
                            // Validate all fields
                            ageError = validateNumber(age, 25f, 100f, "Age")
                            cholError = validateNumber(chol, 100f, 500f, "Cholesterol")
                            thalachError = validateNumber(thalach, 60f, 200f, "Max Heart Rate")
                            oldpeakError = validateNumber(oldpeak, 1f, 10f, "Oldpeak")
                            if (modelType == "Neural Network") {
                                trestbpsError = validateNumber(trestbps, 90f, 200f, "Resting Blood Pressure")
                            }
                            if (age.isBlank() || sex.isBlank() || cp.isBlank() || chol.isBlank() ||
                                thalach.isBlank() || oldpeak.isBlank() || ca.isBlank() || thal.isBlank() ||
                                (modelType == "Neural Network" && (trestbps.isBlank() || exang.isBlank() || slope.isBlank()))
                            ) {
                                return@Button
                            }
                            if (ageError != null || cholError != null || thalachError != null ||
                                oldpeakError != null || (modelType == "Neural Network" && trestbpsError != null)
                            ) {
                                return@Button
                            }

                            val prediction = Prediction(
                                userId = user?.id ?: "",
                                modelType = modelType ?: "",
                                age = age,
                                trestbps = if (modelType == "Neural Network") trestbps else "",
                                chol = chol,
                                thalach = thalach,
                                oldpeak = oldpeak,
                                ca = ca,
                                sex = sex,
                                cp = cp,
                                exang = if (modelType == "Neural Network") exang else "",
                                slope = if (modelType == "Neural Network") slope else "",
                                thal = thal
                            )
                            predictionViewModel.predict(prediction)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = !isLoading
                    ) {
                        Text("Predict Risk")
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownField(
    label: String,
    value: String,
    options: List<Pair<String, String>>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    supportingText: String? = null
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = options.find { it.second == value }?.first ?: "",
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.first) },
                    onClick = {
                        onValueChange(option.second)
                        expanded = false
                    }
                )
            }
        }
        if (supportingText != null) {
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

fun validateNumber(input: String, min: Float, max: Float, field: String): String? {
    if (input.isBlank()) return "$field is required"
    return try {
        val value = input.toFloat()
        if (value < min || value > max) "$field must be between $min and $max"
        else null
    } catch (e: NumberFormatException) {
        "$field must be a valid number"
    }
}