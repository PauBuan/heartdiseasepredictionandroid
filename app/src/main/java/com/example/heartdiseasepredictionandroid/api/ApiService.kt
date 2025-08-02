package com.example.heartdiseasepredictionandroid.api

import com.example.heartdiseasepredictionandroid.model.Prediction
import com.example.heartdiseasepredictionandroid.model.PredictionResult
import com.example.heartdiseasepredictionandroid.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<User>

    @GET("records/{user_id}")
    fun getPredictions(@Path("user_id") userId: String): Call<List<Prediction>>

    @FormUrlEncoded
    @POST("predict")
    fun predict(
        @Field("user_id") userId: String,
        @Field("model_type") modelType: String,
        @Field("age") age: Float,
        @Field("trestbps") trestbps: Float,
        @Field("chol") chol: Float,
        @Field("thalach") thalach: Float,
        @Field("oldpeak") oldpeak: Float,
        @Field("ca") ca: Int,
        @Field("sex") sex: Int,
        @Field("cp") cp: Int,
        @Field("exang") exang: Int,
        @Field("slope") slope: Int,
        @Field("thal") thal: Int
    ): Call<PredictionResult>
}