package com.task.features.presentation.search

import domain.model.City


data class SearchScreenState(
    val data: List<City> = emptyList(),
    val query: String = "",

    )