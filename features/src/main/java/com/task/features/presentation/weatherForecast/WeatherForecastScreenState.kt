package com.task.features.presentation.weatherForecast

import domain.model.CurrentWeather
import domain.model.WeatherForecast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale



data class WeatherForecastScreenState(
    val weather: List<ForecastUiModel>? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

fun WeatherForecast.toUiModel(): List<ForecastUiModel> {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    return weatherData.map { data ->
        val date = Date(data.timestamp * 1000)
        val formattedDate = dateFormatter.format(date)
        ForecastUiModel(
            mainWeather = data.weatherDescriptions.firstOrNull()?.main ?: "Unknown",
            description = data.weatherDescriptions.firstOrNull()?.description ?: "No description",
            icon = data.weatherDescriptions.firstOrNull()?.icon ?: "",
            date = formattedDate,
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

