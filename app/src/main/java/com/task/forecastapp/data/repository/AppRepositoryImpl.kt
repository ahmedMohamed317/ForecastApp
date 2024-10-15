package com.task.forecastapp.data.repository

import com.task.forecastapp.data.RetrofitService
import com.task.forecastapp.data.remote.fromEntityList
import domain.Result
import domain.model.City
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


}