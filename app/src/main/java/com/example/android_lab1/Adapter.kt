package com.example.android_lab1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_lab1.data.modelForDaily.Forecast
import java.text.SimpleDateFormat
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
        val item = getItem(position)

        // 1. Temp
        val temperature = "${item.temp.day.toInt()}°"
        holder.temp.text = temperature

        // 2. Day Of Week
        val date = Date(item.dt * 1000)
        val dayOfWeek = SimpleDateFormat("EEEE", Locale("ru")).format(date)
        holder.day.text = dayOfWeek

        // 3. icon
        val iconCode = item.weather.firstOrNull()?.icon
        if (iconCode != null) {
            val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
            Glide.with(holder.itemView.context)
                .load(iconUrl)
                .into(holder.icon)
        } else {
            holder.icon.setImageResource(R.drawable.ic_launcher_foreground)
        }
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