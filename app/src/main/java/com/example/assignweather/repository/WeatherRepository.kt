package com.example.assignweather.repository

import com.example.assignweather.apiinterface.RetrofitInstance
import com.example.assignweather.model.WeatherResponse
import retrofit2.HttpException
import java.io.IOException

class WeatherRepository {
    suspend fun getWeather(city: String, apiKey: String): WeatherResponse? {
        return try {
            RetrofitInstance.api.getWeather(city, apiKey)
        } catch (e: IOException) {
            null // Network error (e.g., no internet)
        } catch (e: HttpException) {
            null // HTTP error (e.g., invalid city or API key)
        }
    }
}