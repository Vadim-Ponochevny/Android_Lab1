package com.example.android_lab1.data.modelForDaily

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Forecast(
    val dt: Long,
    val temp: Temperature,
    val feels_like: FeelsLike,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Double?,
    val deg: Int?,
    val gust: Double?,
    val clouds: Int?,
    val rain: Double?, // mm
    val snow: Double?, // mm
    val pop: Double?   // 0.0 to 1.0
) : Parcelable
