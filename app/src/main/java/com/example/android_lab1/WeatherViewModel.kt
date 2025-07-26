package com.example.android_lab1


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_lab1.data.model.ForecastResponse
import com.example.android_lab1.data.remote.RetrofitServicesForForecast
import kotlinx.coroutines.launch
import com.example.android_lab1.data.remote.Common

const val apiKey = BuildConfig.apiKeySafe

class WeatherViewModel : ViewModel() {

    val weatherData = MutableLiveData<ForecastResponse>()

    var mService: RetrofitServicesForForecast = Common.retrofitService

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = mService.getForecast(city, apiKey, "metric")
                if (response != null) {
                    Log.d("Response from Weather API", "$response")
                    weatherData.value = response
                }
            }
            catch (e: retrofit2.HttpException) {
                when (e.code()) {
                    401 -> Log.e("API", "401")
                    404 -> Log.e("API", "404")
                    else -> Log.e("API", "Другая HTTP ошибка: ${e.code()}")
                }

            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}
