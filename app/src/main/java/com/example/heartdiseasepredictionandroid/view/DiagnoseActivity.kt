package com.example.heartdiseasepredictionandroid.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.heartdiseasepredictionandroid.R
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class DiagnoseActivity : AppCompatActivity() {
    private val predictionViewModel: PredictionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diagnose)

        val userId = intent.getStringExtra("USER_ID") ?: run {
            Log.e("DiagnoseActivity", "Missing USER_ID")
            Toast.makeText(this, "Error: Missing user ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        val modelType = intent.getStringExtra("MODEL_TYPE") ?: "Neural Network"
        val name = intent.getStringExtra("NAME") ?: "Doctor"

        Log.d("DiagnoseActivity", "Received: userId=$userId, modelType=$modelType, name=$name")

        findViewById<android.widget.TextView>(R.id.tv_header).text = "Enter Patient Data ($modelType)"
        findViewById<android.widget.TextView>(R.id.tv_greeting).text = "Hello, $name"

        val ageEditText: TextInputEditText = findViewById(R.id.et_age)
        val trestbpsEditText: TextInputEditText = findViewById(R.id.et_trestbps)
        val cholEditText: TextInputEditText = findViewById(R.id.et_chol)
        val thalachEditText: TextInputEditText = findViewById(R.id.et_thalach)
        val oldpeakEditText: TextInputEditText = findViewById(R.id.et_oldpeak)
        val caSpinner: Spinner = findViewById(R.id.spinner_ca)
        val sexSpinner: Spinner = findViewById(R.id.spinner_sex)
        val cpSpinner: Spinner = findViewById(R.id.spinner_cp)
        val exangSpinner: Spinner = findViewById(R.id.spinner_exang)
        val slopeSpinner: Spinner = findViewById(R.id.spinner_slope)
        val thalSpinner: Spinner = findViewById(R.id.spinner_thal)
        val predictButton: MaterialButton = findViewById(R.id.btn_predict)

        // Setup spinners
        ArrayAdapter.createFromResource(
            this, R.array.ca_options, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            caSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this, R.array.sex_options, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sexSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this, R.array.cp_options, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cpSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this, R.array.exang_options, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            exangSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this, R.array.slope_options, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            slopeSpinner.adapter = adapter
        }
        ArrayAdapter.createFromResource(
            this, R.array.thal_options, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            thalSpinner.adapter = adapter
        }

        predictionViewModel.predictionResult.observe(this) { result ->
            if (result.isSuccess) {
                val prediction = result.getOrNull()
                val intent = Intent(this, PredictionResultActivity::class.java)
                intent.putExtra("RISK_LEVEL", prediction?.riskLevel)
                intent.putExtra("MESSAGE", prediction?.message)
                intent.putExtra("MODEL_TYPE", prediction?.modelType)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Prediction failed: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
                Log.e("DiagnoseActivity", "Prediction error: ${result.exceptionOrNull()?.message}")
            }
        }

        predictButton.setOnClickListener {
            try {
                // Validate inputs based on HTML form ranges
                val age = ageEditText.text.toString().toFloatOrNull() ?: throw IllegalArgumentException("Invalid age")
                if (age < 25 || age > 100) throw IllegalArgumentException("Age must be between 25 and 100")

                val trestbps = trestbpsEditText.text.toString().toFloatOrNull() ?: throw IllegalArgumentException("Invalid resting blood pressure")
                if (trestbps < 90 || trestbps > 200) throw IllegalArgumentException("Resting BP must be between 90 and 200")

                val chol = cholEditText.text.toString().toFloatOrNull() ?: throw IllegalArgumentException("Invalid cholesterol")
                if (chol < 100 || chol > 500) throw IllegalArgumentException("Cholesterol must be between 100 and 500")

                val thalach = thalachEditText.text.toString().toFloatOrNull() ?: throw IllegalArgumentException("Invalid max heart rate")
                if (thalach < 60 || thalach > 200) throw IllegalArgumentException("Max heart rate must be between 60 and 200")

                val oldpeak = oldpeakEditText.text.toString().toFloatOrNull() ?: throw IllegalArgumentException("Invalid oldpeak")
                if (oldpeak < 0 || oldpeak > 10) throw IllegalArgumentException("Oldpeak must be between 0 and 10")

                val ca = caSpinner.selectedItem.toString().toInt()
                val sex = if (sexSpinner.selectedItem.toString() == "Male") 1 else 0
                val cp = cpSpinner.selectedItemPosition
                val exang = if (exangSpinner.selectedItem.toString() == "Yes") 1 else 0
                val slope = slopeSpinner.selectedItemPosition
                val thal = thalSpinner.selectedItemPosition

                Log.d("DiagnoseActivity", "Submitting: userId=$userId, modelType=$modelType, " +
                        "age=$age, trestbps=$trestbps, chol=$chol, thalach=$thalach, oldpeak=$oldpeak, " +
                        "ca=$ca, sex=$sex, cp=$cp, exang=$exang, slope=$slope, thal=$thal")

                predictionViewModel.predict(userId, modelType, age, trestbps, chol, thalach, oldpeak, ca, sex, cp, exang, slope, thal)
            } catch (e: Exception) {
                Toast.makeText(this, "Please fill all fields correctly: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("DiagnoseActivity", "Input error: ${e.message}")
            }
        }
    }
}