package com.task.features.presentation.weatherDetails

import domain.model.City
import domain.model.CurrentWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class CurrentWeatherScreenState(
    val weather: WeatherUiModel? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

fun CurrentWeather.toUiModel(): WeatherUiModel {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val date = Date(dt * 1000)
    val formattedDate = dateFormatter.format(date)

    return WeatherUiModel(
        mainWeather = weather.firstOrNull()?.main ?: "Unknown",
        description = weather.firstOrNull()?.description ?: "No description",
        icon = weather.firstOrNull()?.icon ?: "",
        date = formattedDate,
        windSpeed = wind.speed,
        cloudiness = clouds.all,
        temperature = main.temp.toInt()

    )
}
data class WeatherUiModel(
    val mainWeather: String,
    val description: String,
    val icon: String,
    val date: String,
    val windSpeed: Double,
    val cloudiness: Int,
    val temperature: Int
)

