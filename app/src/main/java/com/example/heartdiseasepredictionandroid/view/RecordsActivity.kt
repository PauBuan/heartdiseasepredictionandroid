package com.example.heartdiseasepredictionandroid.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.heartdiseasepredictionandroid.R
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.viewmodel.PredictionViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject

class RecordsActivity : AppCompatActivity() {
    private val predictionViewModel: PredictionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        val recyclerView: RecyclerView = findViewById(R.id.rv_predictions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = PredictionAdapter()
        recyclerView.adapter = adapter

        val userId = intent.getStringExtra("USER_ID") ?: return

        predictionViewModel.predictions.observe(this) { result ->
            if (result.isSuccess) {
                adapter.submitList(result.getOrNull() ?: emptyList())
            } else {
                Toast.makeText(this, "Failed to load predictions", Toast.LENGTH_SHORT).show()
            }
        }

        predictionViewModel.getPredictions(userId)
    }

    private class PredictionAdapter : RecyclerView.Adapter<PredictionAdapter.ViewHolder>() {
        private var predictions: List<Prediction> = emptyList()

        class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            val patientText: android.widget.TextView = itemView.findViewById(R.id.tv_patient)
            val dateText: android.widget.TextView = itemView.findViewById(R.id.tv_date)
            val riskScoreText: android.widget.TextView = itemView.findViewById(R.id.tv_risk_score)
            val userIdText: android.widget.TextView = itemView.findViewById(R.id.tv_user_id)
            val modelTypeText: android.widget.TextView = itemView.findViewById(R.id.tv_model_type)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_prediction, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val prediction = predictions[position]
            holder.patientText.text = prediction.username
            holder.dateText.text = prediction.timestamp
            holder.userIdText.text = "User ID: ${prediction.userId}"
            holder.modelTypeText.text = "Model: ${prediction.modelType}"

            // Parse prediction_data JSON
            try {
                val jsonObject = Gson().fromJson(prediction.predictionData, JsonObject::class.java)
                val riskLevel = jsonObject.get("risk_level")?.asString ?: "Unknown"
                holder.riskScoreText.text = "Risk: $riskLevel"
            } catch (e: Exception) {
                holder.riskScoreText.text = "Error parsing risk"
            }
        }

        override fun getItemCount(): Int = predictions.size

        fun submitList(newList: List<Prediction>) {
            predictions = newList
            notifyDataSetChanged()
        }
    }
}