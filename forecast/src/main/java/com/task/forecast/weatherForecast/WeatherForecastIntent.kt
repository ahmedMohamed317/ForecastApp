package com.task.features.presentation.weatherForecast

sealed class WeatherForecastIntent {
    data class FetchWeather(val query: String) : WeatherForecastIntent()
}