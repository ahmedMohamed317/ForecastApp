package com.task.searching.search

import domain.model.City


data class SearchScreenState(
    val data: List<City> = emptyList(),
    val query: String = "",

    )