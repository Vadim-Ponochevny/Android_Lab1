package com.example.android_lab1.data.remote

import com.example.android_lab1.data.model.ForecastResponse
import retrofit2.http.*

interface RetrofitServicesForForecast {
    @GET("forecast")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}