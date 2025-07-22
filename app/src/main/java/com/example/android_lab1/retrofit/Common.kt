package com.example.android_lab1.retrofit


object Common {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    val retrofitService: RetrofitServicesForForecast
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServicesForForecast::class.java)
}