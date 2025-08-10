package com.example.android_lab1.data.modelForCurrentWeather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Main(
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int,
    val temp_min: Double,
    val temp_max: Double,
    val sea_level: Int? = null,
    val grnd_level: Int? = null
) : Parcelable
