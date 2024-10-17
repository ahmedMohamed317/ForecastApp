package com.task.forecastapp.data

import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("geo/1.0/direct")
    suspend fun autoCompleteSearch(
        @Query("apikey") apiKey: String = util.API_KEY,
        @Query("q") q: String = "",
    ): List<CityDataDTO>

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecastByCityName (
        @Query("apikey") apiKey: String = util.API_KEY,
        @Query("q") q: String = "",
    ): WeatherResponseForecastDTO

    @GET("data/2.5/weather")
    suspend fun getCurrentWeatherByCityName (
        @Query("apikey") apiKey: String = util.API_KEY,
        @Query("q") q: String = "",
    ): CurrentWeatherDTO


}