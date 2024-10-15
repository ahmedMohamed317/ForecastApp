package domain.usecase

import domain.Result
import domain.model.City
import domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSearchResultsUseCase(
    private val repository: AppRepository,
) {
    operator fun invoke(query: String): Flow<Result<List<City>>> =
        repository.getSearchResults(query).map { result ->
            when (result) {
                is Result.Success -> Result.Success(result.data.sortedBy { it.name })
                is Result.Error -> Result.Error(result.exception)
                is Result.Loading -> Result.Loading()
            }
        }

}