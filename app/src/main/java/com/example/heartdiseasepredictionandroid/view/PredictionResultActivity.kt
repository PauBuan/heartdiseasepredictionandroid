package com.example.heartdiseasepredictionandroid.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.heartdiseasepredictionandroid.R
import com.google.android.material.textview.MaterialTextView

class PredictionResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prediction_result)

        val riskLevel = intent.getStringExtra("RISK_LEVEL") ?: "Unknown"
        val message = intent.getStringExtra("MESSAGE") ?: "No prediction available"
        val modelType = intent.getStringExtra("MODEL_TYPE") ?: "Unknown"

        findViewById<MaterialTextView>(R.id.tv_risk_level).text = "Risk Level: $riskLevel"
        findViewById<MaterialTextView>(R.id.tv_message).text = message
        findViewById<MaterialTextView>(R.id.tv_model_type).text = "Model: $modelType"
    }
}