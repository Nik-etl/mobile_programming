package com.example.satview

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

data class NasaImageResponse(
    val url: String,
    val date: String
)

interface NasaApiService {
    @GET("planetary/earth/imagery")
    suspend fun getEarthImagery(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("api_key") apiKey: String = "I1A25HNW2ylPKhgb5CwuAPxHCBKgLMhFTp760dkW"  // Replace with your actual key
    ): NasaImageResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.nasa.gov/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val api: NasaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NasaApiService::class.java)
    }
}