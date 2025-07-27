package com.example.android_lab1


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_lab1.data.modelForDaily.WeatherResponse
import com.example.android_lab1.data.remote.RetrofitServicesForDaily
import kotlinx.coroutines.launch
import com.example.android_lab1.data.remote.Common

const val apiKey = BuildConfig.apiKeySafe

class WeatherViewModel : ViewModel() {

    val weatherData = MutableLiveData<WeatherResponse>()

    var mService: RetrofitServicesForDaily = Common.retrofitService

    fun fetchWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = mService.getForecast(city, 16, apiKey, "metric")
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
