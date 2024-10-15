package com.task.features.presentation.search


sealed class SearchScreenEvent {
    data class CitiesSearchResults(val query: String) : SearchScreenEvent()
    data object DisplaySelectedCities : SearchScreenEvent()

}

