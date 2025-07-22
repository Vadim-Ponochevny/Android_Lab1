package com.example.android_lab1


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_lab1.retrofit.RetrofitServicesForForecast
import kotlinx.coroutines.launch
import com.example.android_lab1.retrofit.Common

const val apiKey = BuildConfig.apiKeySafe

class WeatherViewModel : ViewModel() {

    var mService: RetrofitServicesForForecast = Common.retrofitService

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            val response = mService.getForecast(city, apiKey, "metric")
            if (response != null) {
                _weatherData.value = response.body()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}
