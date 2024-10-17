package domain.repository

import domain.Result
import domain.model.City
import domain.model.CurrentWeather
import domain.model.WeatherForecast
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getSearchResults(query: String): Flow<Result<List<City>>>
    fun getCurrentWeatherForCity(query: String): Flow<Result<CurrentWeather>>
    fun getWeatherForecastForCity(query: String): Flow<Result<WeatherForecast>>
}