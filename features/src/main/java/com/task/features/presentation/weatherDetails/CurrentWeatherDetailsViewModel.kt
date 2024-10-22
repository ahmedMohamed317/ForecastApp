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

    var weather = MutableStateFlow( WeatherUiModel("","", "", "", 0.0, 0, 0))
        private set
    val isLoading = MutableStateFlow(true)
    val error = MutableStateFlow<String?>(null)
    val query: StateFlow<String?> = savedStateHandle.getStateFlow("name", null)

    init {
        query.value?.let {
            fetchWeather(it)
        }
    }

    fun fetchWeather(query : String) {
        if (connectivityManager.isNetworkAvailable.value) {
            viewModelScope.launch {
                try {
                    getCurrentWeatherUseCase.invoke(query).onEach {
                        when(it)
                        {
                            is Result.Loading -> isLoading.value = true
                            is Result.Error -> {
                                error.value = it.exception.message
                                isLoading.value=false}
                            is Result.Success -> {
                                weather.value = it.data.toUiModel()
                                isLoading.value = false
                            }
                    }
                }.launchIn(viewModelScope)
                } catch (e: Exception) {
                    error.value = "Failed to load weather"
                    isLoading.value = false
                }
            }
        } else {
            error.value =  "No internet connection"
            isLoading.value = false
        }
    }
}


