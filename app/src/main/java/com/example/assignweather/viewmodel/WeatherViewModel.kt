package com.example.assignweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignweather.model.WeatherResponse
import com.example.assignweather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel:ViewModel() {

    private val repository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val response = repository.getWeather(city, apiKey)
            if (response != null) {
                _weatherData.value = response
                _errorMessage.value = null
            } else {
                _weatherData.value = null
                _errorMessage.value = "Failed to fetch weather data"
            }
        }
    }
}