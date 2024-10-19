package domain.usecase

import domain.Result
import domain.model.*
import domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetCurrentWeatherUseCaseTest {

    private lateinit var repo: AppRepository
    private lateinit var getCurrentWeatherUseCase: GetCurrentWeatherUseCase

    @Before
    fun setup() {
        repo = mockk(relaxed = true, relaxUnitFun = true)
        getCurrentWeatherUseCase = GetCurrentWeatherUseCase(repo)
    }

    @Test
    fun returnListFromRepoWhenInvokeUseCase() = runTest {
        // Given
        val city = "TestCity"
        val testCurrentWeather = CurrentWeather(
            coord = CurrentCoord(
                lon = 40.7128,
                lat = 74.0060
            ),
            weather = listOf(
                CurrentWeatherCondition(
                    id = 800,
                    main = "Clear",
                    description = "clear sky",
                    icon = "01d"
                )
            ),
            base = "stations",
            main = CurrentMain(
                temp = 298.15,
                feelsLike = 300.12,
                tempMin = 295.37,
                tempMax = 301.48,
                pressure = 1013,
                humidity = 65,
                seaLevel = null,
                groundLevel = null
            ),
            visibility = 10000,
            wind = CurrentWind(
                speed = 5.1,
                deg = 220
            ),
            clouds = CurrentClouds(
                all = 0
            ),
            dt = 1633024800,
            sys = CurrentSys(
                type = 1,
                id = 5122,
                country = "US",
                sunrise = 1632993366,
                sunset = 1633036566
            ),
            timezone = -14400,
            id = 5128581,
            name = "New York",
            cod = 200
        )

        // Mock repository behavior
        coEvery { repo.getCurrentWeatherForCity(city)  } returns flowOf(Result.Success(testCurrentWeather))

        // When
        val resultFlow = getCurrentWeatherUseCase(city)

        // Then
        resultFlow.collect { result ->
            assertEquals(Result.Success(testCurrentWeather), result)
        }
    }
}
