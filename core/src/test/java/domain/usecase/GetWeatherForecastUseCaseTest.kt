package domain.usecase

import domain.Result
import domain.model.Clouds
import domain.model.CurrentClouds
import domain.model.CurrentCoord
import domain.model.CurrentMain
import domain.model.CurrentSys
import domain.model.CurrentWeather
import domain.model.CurrentWeatherCondition
import domain.model.CurrentWind
import domain.model.MainData
import domain.model.Sys
import domain.model.WeatherData
import domain.model.WeatherDescription
import domain.model.WeatherForecast
import domain.model.Wind
import domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetWeatherForecastUseCaseTest {


    private lateinit var repo: AppRepository
    private lateinit var getWeatherForecastUseCase: GetWeatherForecastUseCase

    @Before
    fun setup() {
        repo = mockk(relaxed = true, relaxUnitFun = true)
        getWeatherForecastUseCase = GetWeatherForecastUseCase(repo)
    }

    @Test
    fun returnListFromRepoWhenInvokeUseCase() = runTest {
        // Given
        val city = "TestCity"
        val testWeatherForecast = WeatherForecast(
            cod = "200",
            message = 0,
            cnt = 5,
            weatherData = listOf(
                WeatherData(
                    timestamp = 1633024800,
                    mainData = MainData(
                        temperature = 298.15,
                        feelsLike = 300.12,
                        minTemperature = 295.37,
                        maxTemperature = 301.48,
                        pressure = 1013,
                        seaLevel = 1015,
                        groundLevel = 1010,
                        humidity = 65,
                        tempKf = 0.0
                    ),
                    weatherDescriptions = listOf(
                        WeatherDescription(
                            id = 800,
                            main = "Clear",
                            description = "clear sky",
                            icon = "01d"
                        )
                    ),
                    clouds = Clouds(all = 0),
                    wind = Wind(
                        speed = 5.1,
                        degree = 220,
                        gust = 7.5
                    ),
                    visibility = 10000,
                    probabilityOfPrecipitation = 0.0,
                    systemData = Sys(pod = "d"),
                    dateTimeText = "2024-10-18 12:00:00"
                )
            )
        )


        // Mock repository behavior
        coEvery { repo.getWeatherForecastForCity(city)  } returns flowOf(Result.Success(testWeatherForecast))

        // When
        val resultFlow = getWeatherForecastUseCase(city)

        // Then
        resultFlow.collect { result ->
            assertEquals(Result.Success(testWeatherForecast), result)
        }
    }
}