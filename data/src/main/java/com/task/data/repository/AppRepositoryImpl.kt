package com.task.data.repository

import android.util.Log
import com.task.data.RetrofitService
import com.task.data.remote.fromEntityList
import com.task.data.remote.toDomain
import domain.Result
import domain.model.City
import domain.model.CurrentWeather
import domain.model.WeatherForecast
import domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppRepositoryImpl(
    private val retrofitService: RetrofitService,
) : AppRepository {

    override fun getSearchResults(query: String): Flow<Result<List<City>>> = flow {
        try {
            emit(Result.Loading())
            val response = retrofitService.autoCompleteSearch(q = query)
            emit(Result.Success(response.fromEntityList()))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getWeatherForecastForCity(query: String): Flow<Result<WeatherForecast>> = flow {
        try {
            emit(Result.Loading())
            val response = retrofitService.getWeatherForecastByCityName(q = query)
            emit(Result.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

    override fun getCurrentWeatherForCity(query: String): Flow<Result<CurrentWeather>> = flow {
        try {
            Log.d("AppRepositoryImpl", "getCurrentWeatherForCity: $query")
            emit(Result.Loading())
            val response = retrofitService.getCurrentWeatherByCityName(q = query)
            emit(Result.Success(response.toDomain()))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }


}