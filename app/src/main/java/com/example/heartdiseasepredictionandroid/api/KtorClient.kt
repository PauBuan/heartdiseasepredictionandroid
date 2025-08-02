package com.example.heartdiseasepredictionandroid.api

import com.example.heartdiseasepredictionandroid.model.LoginResponse
import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.model.Record
import com.example.heartdiseasepredictionandroid.model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    suspend fun register(name: String, username: String, password: String, specialty: String?): Result<User> {
        return try {
            val response = client.post("http://10.0.2.2:5000/api/android/register") {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(
                    listOf(
                        "name" to name,
                        "username" to username,
                        "password" to password,
                        "specialty" to specialty
                    ).filter { it.second != null }.formUrlEncode()
                )
            }
            if (response.status == HttpStatusCode.Created) {
                Result.success(Json.decodeFromString<User>(response.bodyAsText()))
            } else {
                Result.failure(Exception("Registration failed: ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(username: String, password: String): Result<LoginResponse> {
        return try {
            val response = client.post("http://10.0.2.2:5000/api/android/login") {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(
                    listOf(
                        "username" to username,
                        "password" to password
                    ).formUrlEncode()
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(Json.decodeFromString<LoginResponse>(response.bodyAsText()))
            } else {
                Result.failure(Exception("Login failed: ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun predict(prediction: Prediction): Result<PredictionResult> {
        return try {
            val response = client.post("http://10.0.2.2:5000/api/android/predict") {
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(
                    listOf(
                        "user_id" to prediction.userId,
                        "model_type" to prediction.modelType,
                        "age" to prediction.age,
                        "trestbps" to prediction.trestbps,
                        "chol" to prediction.chol,
                        "thalach" to prediction.thalach,
                        "oldpeak" to prediction.oldpeak,
                        "ca" to prediction.ca,
                        "sex" to prediction.sex,
                        "cp" to prediction.cp,
                        "exang" to prediction.exang,
                        "slope" to prediction.slope,
                        "thal" to prediction.thal
                    ).filter { it.second.isNotBlank() }.formUrlEncode()
                )
            }
            if (response.status == HttpStatusCode.OK) {
                Result.success(Json.decodeFromString<PredictionResult>(response.bodyAsText()))
            } else {
                Result.failure(Exception("Prediction failed: ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRecords(userId: String): Result<List<Record>> {
        return try {
            val response = client.get("http://10.0.2.2:5000/api/android/records/$userId")
            if (response.status == HttpStatusCode.OK) {
                Result.success(Json.decodeFromString<List<Record>>(response.bodyAsText()))
            } else {
                Result.failure(Exception("Failed to fetch records: ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllRecords(): Result<List<Record>> {
        return try {
            val response = client.get("http://10.0.2.2:5000/api/android/records/all")
            if (response.status == HttpStatusCode.OK) {
                Result.success(Json.decodeFromString<List<Record>>(response.bodyAsText()))
            } else {
                Result.failure(Exception("Failed to fetch all records: ${response.bodyAsText()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}