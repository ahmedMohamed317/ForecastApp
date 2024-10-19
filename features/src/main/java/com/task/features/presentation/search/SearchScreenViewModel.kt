package com.task.features.presentation.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel
import domain.usecase.GetSearchResultsUseCase
import domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val searchUseCases: GetSearchResultsUseCase,
    private val connectivityManager: util.ConnectivityManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state
    private val _eventFlow = MutableSharedFlow<UiEvent>(replay = 1)
    val eventFlow = _eventFlow.asSharedFlow()
    var loading = mutableStateOf(false)
        private set

    init {
        savedStateHandle.get<String>("q")?.let { query ->
            onQueryChanged(query)
        }
    }

    fun onQueryChanged(newQuery: String) {
        _state.value = _state.value.copy(query = newQuery)
        if (newQuery.isNotBlank()) {
            onEvent(SearchScreenEvent.CitiesSearchResults(newQuery))
        }
    }

    fun clearQuery() {
        Log.d("SearchScreenViewModel", "cleanQuery")
        _state.value = _state.value.copy(query = "", data = emptyList())
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.CitiesSearchResults -> {
                viewModelScope.launch {
                    doSearch(event.query)
                }
            }
            else -> Unit
        }
    }

    private suspend fun doSearch(query: String) {

        if (!connectivityManager.isNetworkAvailable.value) {

            _eventFlow.emit(
                UiEvent.ShowSnackbar("No network available")
            )
            return
        }

        searchUseCases.invoke(query)
            .onEach { result ->
                loading.value = true
                when (result) {
                    is Result.Success -> {
                        loading.value = false
                        _state.value = _state.value.copy(data = result.data)
                    }
                    is Result.Error -> {
                        loading.value = false
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(result.exception.message ?: "Unknown error")
                        )
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}
