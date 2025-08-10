package com.example.android_lab1.data.modelForCurrentWeather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sys(
    val type: Int? = null,
    val id: Int? = null,
    val message: Double? = null,
    val country: String,
    val sunrise: Long,
    val sunset: Long
) : Parcelable
