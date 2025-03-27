package com.example.assignweather

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.assignweather.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private val apiKey = "YOUR_API_KEY" // Replace with your OpenWeatherMap API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]

        val cityEditText = findViewById<EditText>(R.id.cityEditText)
        val fetchButton = findViewById<Button>(R.id.fetchButton)
        val cityTextView = findViewById<TextView>(R.id.cityTextView)
        val temperatureTextView = findViewById<TextView>(R.id.temperatureTextView)
        val descriptionTextView = findViewById<TextView>(R.id.descriptionTextView)
        val humidityTextView = findViewById<TextView>(R.id.humidityTextView)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)

        fetchButton.setOnClickListener {
            val city = cityEditText.text.toString().trim()
            if (city.isNotEmpty()) {
                viewModel.fetchWeather(city, apiKey)
            }
        }

        viewModel.weatherData.observe(this) { weather ->
            if (weather != null) {
                cityTextView.text = weather.name
                temperatureTextView.text = "Temperature: ${weather.main.temp}°C"
                descriptionTextView.text = "Description: ${weather.weather[0].description}"
                humidityTextView.text = "Humidity: ${weather.main.humidity}%"
                errorTextView.text = ""
            } else {
                clearWeatherData(cityTextView, temperatureTextView, descriptionTextView, humidityTextView)
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                errorTextView.text = error
            } else {
                errorTextView.text = ""
            }
        }
    }

    private fun clearWeatherData(vararg textViews: TextView) {
        textViews.forEach { it.text = "" }
    }
}
