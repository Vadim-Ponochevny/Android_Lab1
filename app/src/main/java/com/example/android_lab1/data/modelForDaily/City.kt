package com.example.android_lab1.data.modelForDaily

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Int?,
    val name: String?,
    val coord: Coord,
) : Parcelable

