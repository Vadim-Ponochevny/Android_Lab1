package com.example.android_lab1.data.modelForCurrentWeather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentWeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String?,
    val main: Main,
    val visibility: Int?,
    val wind: Wind?,
    val clouds: Clouds?,
    val rain: Rain?,
    val snow: Snow?,
    val dt: Long,
    val sys: Sys,
    val timezone: Int?,
    val id: Int,
    val name: String,
    val cod: Int?
) : Parcelable
