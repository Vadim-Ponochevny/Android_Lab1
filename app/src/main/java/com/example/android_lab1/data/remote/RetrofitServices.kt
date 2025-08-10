package com.example.android_lab1.data.remote

import com.example.android_lab1.data.modelForCurrentWeather.CurrentWeatherResponse
import com.example.android_lab1.data.modelForDaily.WeatherResponse
import retrofit2.http.*

interface RetrofitServices {
    @GET("forecast/daily")
    suspend fun getDailyForecast(
        @Query("q") city: String,
        @Query("cnt") numberOfDays: Int,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): CurrentWeatherResponse



}