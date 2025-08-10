package com.example.android_lab1


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_lab1.data.modelForCurrentWeather.CurrentWeatherResponse
import com.example.android_lab1.data.modelForDaily.WeatherResponse
import com.example.android_lab1.data.remote.RetrofitServices
import kotlinx.coroutines.launch
import com.example.android_lab1.data.remote.Common
import kotlinx.coroutines.async

const val apiKey = BuildConfig.apiKeySafe

class WeatherViewModel : ViewModel() {
    private val _dailyWeatherData = MutableLiveData<WeatherResponse>()
    val dailyWeatherData: LiveData<WeatherResponse> get() = _dailyWeatherData

    private val _currentWeatherData = MutableLiveData<CurrentWeatherResponse>()
    val currentWeatherData:  LiveData<CurrentWeatherResponse> get() = _currentWeatherData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> get() = _errorLiveData

    var mService: RetrofitServices = Common.retrofitService

    fun fetchWeather(city: String, unitParam: String) {
        viewModelScope.launch {
            try {
                val dailyForecastResponse = async {
                    mService.getDailyForecast(city, 16, apiKey, unitParam)
                }
                val currentWeatherResponse = async {
                    mService.getCurrentWeather(city, apiKey, unitParam)
                }
                _dailyWeatherData.value = dailyForecastResponse.await()
                _currentWeatherData.value = currentWeatherResponse.await()

                Log.d("1Response from Weather API", "$dailyForecastResponse")
                Log.d("2Response from Weather API", "$currentWeatherResponse")
            }
            catch (e: retrofit2.HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "401 error"
                    404 -> "404 error $city is not found"
                    else -> "HTTP ошибка: ${e.code()}"
                }
                _errorLiveData.value = errorMessage

            }
        }
    }



    override fun onCleared() {
        super.onCleared()
    }
}
