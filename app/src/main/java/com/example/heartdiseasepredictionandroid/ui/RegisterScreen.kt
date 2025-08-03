package com.example.heartdiseasepredictionandroid.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.heartdiseasepredictionandroid.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var specialty by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) } // For dropdown menu
    val user by authViewModel.user.collectAsState()
    val errorMessage by authViewModel.errorMessage.collectAsState()
    val isLoading by authViewModel.isLoading.collectAsState()

    // Specialty options
    val specialtyOptions = listOf(
        "" to "Select Specialty",
        "Cardiologist" to "Cardiologist",
        "Interventional Cardiologist" to "Interventional Cardiologist",
        "Electrophysiologist" to "Electrophysiologist",
        "Cardiac Surgeon" to "Cardiac Surgeon",
        "General Practitioner (GP)" to "General Practitioner (GP)"
    )

    LaunchedEffect(user) {
        user?.let {
            Log.d("RegisterScreen", "Registration successful: ${it.username}")
            Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
            navController.navigate("main") {
                popUpTo("register") { inclusive = true }
            }
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Log.e("RegisterScreen", "Registration error: $it")
            Toast.makeText(context, "Registration error: $it", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )
        Spacer(modifier = Modifier.height(8.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { if (!isLoading) expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = specialtyOptions.find { it.first == specialty }?.second ?: "Select Specialty",
                onValueChange = {}, // Read-only, selection via dropdown
                label = { Text("Specialty (optional)") },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                enabled = !isLoading
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                specialtyOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.second) },
                        onClick = {
                            specialty = option.first
                            expanded = false
                            Log.d("RegisterScreen", "Selected specialty: ${option.first}")
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (name.isBlank() || username.isBlank() || password.isBlank()) {
                    Toast.makeText(context, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("RegisterScreen", "Submitting: username=$username, specialty=$specialty")
                    authViewModel.register(name, username, password, specialty.ifBlank { null })
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Register")
        }
    }
}