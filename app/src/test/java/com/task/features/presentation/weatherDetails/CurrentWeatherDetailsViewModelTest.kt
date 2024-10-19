package com.task.features.presentation.weatherDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import domain.Result
import domain.model.CurrentWeather
import domain.model.WeatherData
import domain.usecase.GetCurrentWeatherUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
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
class CurrentWeatherDetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: CurrentWeatherDetailsViewModel
    private val getCurrentWeatherUseCase = mockk<GetCurrentWeatherUseCase>(relaxed = true)
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var savedStateHandle: SavedStateHandle
    private val networkAvailableStateFlow = MutableStateFlow(true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle(mapOf("name" to "Cairo"))
        connectivityManager = mockk(relaxed = true)
        every { connectivityManager.isNetworkAvailable } returns mutableStateOf( networkAvailableStateFlow.value)
        viewModel = CurrentWeatherDetailsViewModel(getCurrentWeatherUseCase, connectivityManager, savedStateHandle)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `fetchWeather should update state when data is fetched successfully`() = runTest {
        // Given
        val weatherData = mockk<CurrentWeather>(relaxed = true)
        every { connectivityManager.isNetworkAvailable.value } returns true
        coEvery { getCurrentWeatherUseCase.invoke("Cairo") } returns flowOf(Result.Success(weatherData))

        // When
        viewModel.fetchWeather("Cairo")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(weatherData.toUiModel(), viewModel.uiState.value.weather)
        assertEquals(null, viewModel.uiState.value.error)
    }


    @Test
    fun `fetchWeather should update state on error`() = runTest {
        // Given
        val errorMessage = "Error fetching weather"
        coEvery { getCurrentWeatherUseCase.invoke("Cairo") } returns flowOf(Result.Error(Exception(errorMessage)))

        // When
        viewModel.fetchWeather("Cairo")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(errorMessage, viewModel.uiState.value.error)
    }

    @Test
    fun `fetchWeather should update state when there is no network connection`() = runTest {
        // Given
        networkAvailableStateFlow.value = false
        every { connectivityManager.isNetworkAvailable.value } returns false

        // When
        viewModel.fetchWeather("Cairo")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals("No internet connection", viewModel.uiState.value.error)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }
}


