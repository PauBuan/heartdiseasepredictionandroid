package com.example.heartdiseasepredictionandroid.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.heartdiseasepredictionandroid.R
import com.example.heartdiseasepredictionandroid.viewmodel.AuthViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class RegisterActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEditText: TextInputEditText = findViewById(R.id.et_name) // Add name field
        val usernameEditText: TextInputEditText = findViewById(R.id.et_username)
        val passwordEditText: TextInputEditText = findViewById(R.id.et_password)
        val registerButton: MaterialButton = findViewById(R.id.btn_register)

        authViewModel.registerResult.observe(this) { result ->
            if (result.isSuccess) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, result.exceptionOrNull()?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.register(name, username, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}