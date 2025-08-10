package com.example.android_lab1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_lab1.data.modelForDaily.Forecast
import com.example.android_lab1.databinding.RviewItemBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Adapter(
) : ListAdapter<Forecast, Adapter.ViewHolder>(WeatherDiffCallback()) {

    inner class ViewHolder(val binding: RviewItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        // 1. Temp
        holder.binding.temperature.text = holder.itemView.context.getString(R.string.temp_text, item.temp.day.toString())
        // 2. Day Of Week
        val date = Date(item.dt * 1000)
        holder.binding.dayOfWeek.text = SimpleDateFormat("EEEE", Locale("ru")).format(date)

        // 3. icon
        item.weather.firstOrNull()?.icon?.let {
            val iconUrl = "https://openweathermap.org/img/wn/${it}@2x.png"
            Glide.with(holder.itemView.context)
                .load(iconUrl)
                .into(holder.binding.temperatureIcon)
        } ?: {
            holder.binding.temperatureIcon.setImageResource(R.drawable.ic_launcher_foreground)
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