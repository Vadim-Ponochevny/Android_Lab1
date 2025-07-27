package com.example.android_lab1.data.remote

import com.example.android_lab1.data.modelForDaily.WeatherResponse
import retrofit2.http.*

interface RetrofitServicesForDaily {
    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("cnt") numberOfDays: Int,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}