package com.task.features.presentation.weatherForecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import domain.Result
import domain.model.WeatherForecast
import domain.usecase.GetWeatherForecastUseCase
import io.mockk.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.ConnectivityManager

@ExperimentalCoroutinesApi
class WeatherForecastViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: WeatherForecastViewModel
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase = mockk()
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()
    private val networkAvailableStateFlow = MutableStateFlow(true)


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle(mapOf("name" to "Cairo"))
        connectivityManager = mockk(relaxed = true)
        every { connectivityManager.isNetworkAvailable } returns mutableStateOf( networkAvailableStateFlow.value)
        viewModel = WeatherForecastViewModel(getWeatherForecastUseCase, connectivityManager, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `fetchWeather should update state when data is fetched successfully`() = runTest {
        // Given
        val mockWeatherForecast = mockk<WeatherForecast>(relaxed = true)
        every { connectivityManager.isNetworkAvailable.value } returns true
        coEvery { getWeatherForecastUseCase.invoke("Cairo") } returns flowOf(Result.Success(mockWeatherForecast))

        // When
        viewModel.fetchWeather("Cairo")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertNotNull(viewModel.uiState.value.weather)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `fetchWeather should handle error state`() = runTest {
        // Given
        every { connectivityManager.isNetworkAvailable.value } returns true
        coEvery { getWeatherForecastUseCase.invoke("Cairo") } returns flowOf(Result.Error(Exception("Network error")))

        // When
        viewModel.fetchWeather("Cairo")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.weather)
        assertEquals("Network error", viewModel.uiState.value.error)
    }

    @Test
    fun `fetchWeather should show no internet connection error`() = runTest {
        // Given
        every { connectivityManager.isNetworkAvailable.value } returns false

        // When
        viewModel.fetchWeather("Cairo")

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.weather)
        assertEquals("No internet connection", viewModel.uiState.value.error)
    }
}
