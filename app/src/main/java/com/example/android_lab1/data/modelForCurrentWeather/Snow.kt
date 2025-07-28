package com.example.android_lab1.data.modelForCurrentWeather

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Snow(
    val oneHour: Double? = null
) : Parcelable