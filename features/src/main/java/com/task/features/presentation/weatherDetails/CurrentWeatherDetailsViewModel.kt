package com.task.features.presentation.weatherDetails

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import domain.usecase.GetSearchResultsUseCase
import domain.Result
import domain.usecase.GetCurrentWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class CurrentWeatherDetailsViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val connectivityManager: util.ConnectivityManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(CurrentWeatherScreenState())
    val uiState: StateFlow<CurrentWeatherScreenState> = _uiState

    val query: StateFlow<String?> = savedStateHandle.getStateFlow("name", null)

    init {
        query.value?.let {
            fetchWeather(it)
        }
    }

    fun fetchWeather(query : String) {
        if (connectivityManager.isNetworkAvailable.value) {
            viewModelScope.launch {
                _uiState.value = _uiState.value.copy(isLoading = true)
                try {
                    getCurrentWeatherUseCase.invoke(query).onEach {
                        when(it)
                        {
                            is Result.Loading ->_uiState.value = _uiState.value.copy(weather = null, isLoading = true)
                            is Result.Error ->_uiState.value = _uiState.value.copy(error = it.exception.message, isLoading = false)
                            is Result.Success ->_uiState.value = _uiState.value.copy(weather =it.data.toUiModel(), isLoading = false)
                    }
                }.launchIn(viewModelScope)
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(error = "Failed to load weather", isLoading = false)
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(error = "No internet connection", isLoading = false)
        }
    }
}


