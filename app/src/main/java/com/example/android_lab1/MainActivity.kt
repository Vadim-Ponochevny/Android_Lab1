package com.example.android_lab1

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_lab1.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object Constants {
    const val CELSIUS = "C"
    const val FAHRENHEIT = "F"
    const val UNIT_METRIC = "metric"
    const val UNIT_IMPERIAL = "imperial"
}

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private var adapter = Adapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        enableEdgeToEdge()

        setupSystemBarsPadding()
        setUpRecyclerView()
        initViewModel()
        observersForLifeData()
        setUpListeners()
        displayCurrentDay()
        spinner()
        binding.contentGroup.visibility = View.GONE
    }

    private fun setupSystemBarsPadding() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun setUpRecyclerView() {
        binding.rView.setHasFixedSize(true)
        binding.rView.layoutManager = LinearLayoutManager(this)
        binding.rView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    private fun observersForLifeData() {
        viewModel.dailyWeatherData.observe(this) { weatherResponse ->
            adapter.submitList(weatherResponse.list)
        }

        viewModel.currentWeatherData.observe(this) { weather ->
            binding.fieldForCity.text = weather.name
            weather.weather.firstOrNull()?.icon?.let {
                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/${it}@2x.png")
                    .into(binding.iconOfTemp)
            }
            binding.fieldForTemp.text = getString(R.string.temp_text, weather.main.temp.toInt().toString())
            binding.fieldForPressure.text = getString(R.string.pressure_text, weather.main.pressure.toString())
            binding.contentGroup.visibility = View.VISIBLE
        }

        viewModel.errorLiveData.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpListeners() {
        binding.button.setOnClickListener {
            val receivedCity = binding.editText.text.toString()
            val selectedDegree = binding.spinner.selectedItem.toString()

            if (receivedCity.isNotEmpty()) {
                val unitParam = when (selectedDegree) {
                    Constants.CELSIUS -> Constants.UNIT_METRIC
                    Constants.FAHRENHEIT -> Constants.UNIT_IMPERIAL
                    else -> Constants.UNIT_METRIC
                }
                viewModel.fetchWeather(receivedCity, unitParam)
            }
            hideKeyboard()
        }
    }

    private fun displayCurrentDay() {
        val calendar = Calendar.getInstance()
        val dayOfWeek = SimpleDateFormat("EEEE", Locale("ru")).format(calendar.time)
        binding.fieldForCurrentDayOfWeek.text = dayOfWeek
    }

    private fun spinner() {
        val degrees = resources.getStringArray(R.array.degrees)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, degrees
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDegree = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
    }

}

