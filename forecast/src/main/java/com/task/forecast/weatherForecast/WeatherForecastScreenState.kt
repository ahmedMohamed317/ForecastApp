package com.task.forecast.weatherForecast

import domain.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.task.forecastutilis.toFormattedDate


data class WeatherForecastScreenState(
    val weather: List<ForecastUiModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

fun WeatherForecast.toUiModel(): List<ForecastUiModel> {
    return weatherData.map { data ->
        ForecastUiModel(
            mainWeather = data.weatherDescriptions.firstOrNull()?.main ?: "Unknown",
            description = data.weatherDescriptions.firstOrNull()?.description ?: "No description",
            icon = data.weatherDescriptions.firstOrNull()?.icon ?: "",
            date = data.timestamp.toFormattedDate(),
            windSpeed = data.wind.speed,
            cloudiness = data.clouds.all,
            temperature = data.mainData.temperature.toInt()
        )
    }
}

data class ForecastUiModel(
    val mainWeather: String,
    val description: String,
    val icon: String,
    val date: String,
    val windSpeed: Double,
    val cloudiness: Int,
    val temperature: Int
)

