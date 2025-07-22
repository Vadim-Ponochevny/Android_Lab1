package com.example.android_lab1.retrofit

import com.example.android_lab1.data.ForecastResponse
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServicesForForecast {
    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}