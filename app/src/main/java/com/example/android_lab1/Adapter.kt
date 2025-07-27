package com.example.android_lab1

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_lab1.data.modelForDaily.Forecast
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date
import java.util.Locale


class Adapter(
) : ListAdapter<Forecast, Adapter.ViewHolder>(WeatherDiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.dayOfWeek)
        val icon: ImageView = itemView.findViewById(R.id.temperature_icon)
        val temp: TextView = itemView.findViewById(R.id.temperature)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastItem = getItem(position)
        val iconUrl = "https://openweathermap.org/img/wn/${forecastItem.weather[0].icon}@2x.png"
        val degrees = forecastItem.temp.day.toInt().toString() + "°"
//        val dayOfWeek = SimpleDateFormat("EEEE", Locale("ru")).format(forecastItem.dt.toInt())
        val timestampInSeconds = forecastItem.dt.toLong()
        val timestampInMillis = timestampInSeconds * 1000  // обязательно!

        val date = Date(timestampInMillis)

        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale("ru"))
        val formattedDate = formatter.format(date)

        val dayFormat = SimpleDateFormat("EEEE", Locale("ru"))
        val dayOfWeek = dayFormat.format(date)
        holder.temp.text = degrees
        holder.day.text = dayOfWeek
        Glide.with(holder.itemView.context)
            .load(iconUrl)
            .into(holder.icon)
    }
}


class WeatherDiffCallback : DiffUtil.ItemCallback<Forecast>() {
    override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
        return oldItem.dt == newItem.dt
    }
}