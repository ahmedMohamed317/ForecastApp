package com.task.features.presentation.weatherForecast

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.Result
import domain.usecase.GetWeatherForecastUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val getWeatherForecastUseCase: GetWeatherForecastUseCase,
    private val connectivityManager: util.ConnectivityManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherForecastScreenState())
    val uiState: StateFlow<WeatherForecastScreenState> = _uiState
    val query: StateFlow<String?> = savedStateHandle.getStateFlow("name", null)
    private val _intentChannel = MutableStateFlow<WeatherForecastIntent?>(null)

    init {
        viewModelScope.launch {
            _intentChannel.collect { intent ->
                when (intent) {
                    is WeatherForecastIntent.FetchWeather -> {
                        fetchWeather(intent.query)
                    }

                    else -> {}
                }
            }
        }

        val query = savedStateHandle.get<String>("name")
        if (query != null) {
            processIntent(WeatherForecastIntent.FetchWeather(query))
        }
    }

    private fun processIntent(intent: WeatherForecastIntent) {
        _intentChannel.value = intent
    }

    fun fetchWeather(query: String) {
        if (connectivityManager.isNetworkAvailable.value) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                try {
                    getWeatherForecastUseCase.invoke(query).onEach { result ->
                        when (result) {
                            is Result.Loading -> {
                                _uiState.value = _uiState.value.copy(isLoading = true)
                            }
                            is Result.Success -> {
                                _uiState.value = _uiState.value.copy(
                                    weather = result.data.toUiModel(),
                                    isLoading = false,
                                    error = null
                                )
                            }
                            is Result.Error -> {
                                _uiState.value = _uiState.value.copy(
                                    error = result.exception.message,
                                    isLoading = false
                                )
                            }
                        }
                    }.launchIn(viewModelScope)
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        error = "Failed to load weather",
                        isLoading = false
                    )
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(error = "No internet connection")
        }
    }
}
