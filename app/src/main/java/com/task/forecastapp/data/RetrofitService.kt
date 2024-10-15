package com.task.forecastapp.data

import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitService {

    @GET("direct")
    suspend fun autoCompleteSearch(
        @Query("apikey") apiKey: String = util.API_KEY,
        @Query("q") q: String = "",
    ): List<CityDataDTO>

}