package com.example.android_lab1

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var vm: WeatherViewModel
    private var adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val recyclerView = findViewById<RecyclerView>(R.id.rView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        vm = ViewModelProvider(this)[WeatherViewModel::class.java]

        val textViewOfCity = findViewById<TextView>(R.id.fieldForCity)
        val sendButton = findViewById<Button>(R.id.button)

        vm.weatherData.observe(this, { weather ->
            adapter.submitList(weather.list)

        })

        sendButton.setOnClickListener {
            val cityName = findViewById<EditText>(R.id.edit_text)
            val receivedCity = cityName.text.toString()
            textViewOfCity.text = receivedCity

            vm.fetchWeather(receivedCity)
        }

        val calendar = Calendar.getInstance()
        val dayOfWeek = SimpleDateFormat("EEEE", Locale("ru")).format(calendar.time)

        val data = findViewById<TextView>(R.id.fieldForData)
        data.text = dayOfWeek


    }
}