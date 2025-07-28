package com.example.android_lab1

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var vm: WeatherViewModel
    private var adapter = Adapter()

    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewOfCity: TextView
    private lateinit var icon: ImageView
    private lateinit var temp: TextView
    private lateinit var pressure: TextView
    private lateinit var data: TextView
    private lateinit var sendButton: Button
    private lateinit var InputcityName: EditText
    private lateinit var group: Group

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        InputcityName = findViewById<EditText>(R.id.edit_text)
        sendButton = findViewById<Button>(R.id.button)
        textViewOfCity = findViewById<TextView>(R.id.fieldForCity)
        data = findViewById<TextView>(R.id.fieldForCurrentDayOfWeek)
        icon = findViewById<ImageView>(R.id.iconOfTemp)
        temp = findViewById<TextView>(R.id.fieldForTemp)
        pressure = findViewById<TextView>(R.id.fieldForPressure)
        group = findViewById<Group>(R.id.contentGroup)
        recyclerView = findViewById<RecyclerView>(R.id.rView)


        setupSystemBarsPadding()
        setUpRecyclerView()
        initViewModel()
        observersForLifeData()
        setUpListeners()
        displayCurrentDay()
        group.visibility = View.GONE
    }

    private fun setupSystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun setUpRecyclerView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        vm = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    private fun observersForLifeData() {
        vm.dailyWeatherData.observe(this, { weatherResponse ->
            adapter.submitList(weatherResponse.list)
        })

        vm.currentWeatherData.observe(this) { weather ->
            val iconCode = weather.weather.firstOrNull()?.icon
            textViewOfCity.text = weather.name
            iconCode?.let {
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/${it}@2x.png")
                    .into(icon)
            }
            val currentTemp = weather.main.temp.toInt().toString() + "°"
            temp.text = currentTemp
            val currentPressure = "Атм. давление: " + weather.main.pressure.toString()
            pressure.text = currentPressure
            group.visibility = View.VISIBLE
        }

        vm.errorLiveData.observe(this) {error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpListeners() {
        sendButton.setOnClickListener {
            val receivedCity = InputcityName.text.toString()
            if (receivedCity != null) {
                vm.fetchWeather(receivedCity)
            }
            hideKeyboard()
        }
    }

    private fun displayCurrentDay() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = SimpleDateFormat("EEEE", Locale("ru")).format(calendar.time)
        data.text = dayOfWeek
    }

}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}