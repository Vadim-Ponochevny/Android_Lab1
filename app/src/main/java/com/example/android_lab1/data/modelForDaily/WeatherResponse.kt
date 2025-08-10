package com.example.android_lab1.data.modelForDaily

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponse(
    val city: City,
    val country: String?,
    val population: Int?,
    val timezone: Int?,
    val cod: String?,
    val message: Double?,
    val cnt: Int?,
    val list: List<Forecast>
) : Parcelable
