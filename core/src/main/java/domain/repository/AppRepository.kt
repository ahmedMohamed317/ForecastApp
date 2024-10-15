package domain.repository

import domain.Result
import domain.model.City
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getSearchResults(query: String): Flow<Result<List<City>>>
}