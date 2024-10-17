package domain.usecase

import domain.Result
import domain.model.City
import domain.model.CurrentWeather
import domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentWeatherUseCase(
    private val repository: AppRepository,
) {
    operator fun invoke(query: String): Flow<Result<CurrentWeather>> =
        repository.getCurrentWeatherForCity(query)
}