package com.task.forecast.weatherForecast

sealed interface WeatherForecastIntent {
    data object FetchWeather : WeatherForecastIntent
}