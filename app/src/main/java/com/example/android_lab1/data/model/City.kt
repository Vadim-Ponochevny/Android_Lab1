package com.example.android_lab1.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
) : Parcelable
