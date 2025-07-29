package com.example.android_lab1


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_lab1.data.modelForCurrentWeather.CurrentWeatherResponse
import com.example.android_lab1.data.modelForDaily.WeatherResponse
import com.example.android_lab1.data.remote.RetrofitServices
import kotlinx.coroutines.launch
import com.example.android_lab1.data.remote.Common

const val apiKey = BuildConfig.apiKeySafe

class WeatherViewModel : ViewModel() {

    val dailyWeatherData = MutableLiveData<WeatherResponse>()
    val currentWeatherData = MutableLiveData<CurrentWeatherResponse>()
    val errorLiveData = MutableLiveData<String>()

    var mService: RetrofitServices = Common.retrofitService

    fun fetchWeather(city: String, unitParam: String) {
        viewModelScope.launch {
            try {
                val dailyForecastResponse = mService.getDailyForecast(city, 16, apiKey, unitParam)
                val currentWeatherResponse = mService.getCurrentWeather(city, apiKey, unitParam)

                if ((dailyForecastResponse != null) and (currentWeatherResponse != null)) {
                    Log.d("1Response from Weather API", "$dailyForecastResponse")
                    Log.d("2Response from Weather API", "$currentWeatherResponse")
                    dailyWeatherData.value = dailyForecastResponse
                    currentWeatherData.value = currentWeatherResponse

                }
            }
            catch (e: retrofit2.HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "401 error"
                    404 -> "404 error ${city} is not found"
                    else -> "HTTP ошибка: ${e.code()}"
                }
                errorLiveData.value = errorMessage

            }
        }
    }



    override fun onCleared() {
        super.onCleared()
    }
}
