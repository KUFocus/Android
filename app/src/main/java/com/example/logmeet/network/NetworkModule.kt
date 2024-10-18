package com.example.logmeet.network

import com.example.logmeet.data.api.AuthController
import com.example.logmeet.data.api.MinutesController
import com.example.logmeet.data.api.ProjectController
import com.example.logmeet.data.api.ScheduleController
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://13.124.14.33:8080/"

object RetrofitClient {
    private val retrofit = getRetrofit()
    val auth_instance: AuthController = retrofit.create(AuthController::class.java)
    val project_instance: ProjectController = retrofit.create(ProjectController::class.java)
    val schedule_instance: ScheduleController = retrofit.create(ScheduleController::class.java)
    val minutes_instance: MinutesController = retrofit.create(MinutesController::class.java)

    private val jsonRetrofit = getJsonRetrofit()
    val authInstance =
        requireNotNull(jsonRetrofit.create(AuthController::class.java)) { "NetworkModule's AuthController is null" }
    val projectInstance =
        requireNotNull(jsonRetrofit.create(ProjectController::class.java)) { "NetworkModule's ProjectController is null" }
}

fun getJsonRetrofit(): Retrofit {
    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .client(okHttpClient())
        .build()
}

fun okHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    return builder.addInterceptor(logging).build()
}

fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient())
        .build()
}