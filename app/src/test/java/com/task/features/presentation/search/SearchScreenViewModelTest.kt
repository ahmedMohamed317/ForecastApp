package com.task.features.presentation.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import domain.Result
import domain.model.City
import domain.usecase.GetSearchResultsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import util.ConnectivityManager

@OptIn(ExperimentalCoroutinesApi::class)
class SearchScreenViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var getSearchResultsUseCase: GetSearchResultsUseCase
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getSearchResultsUseCase = mockk(relaxed = true)
        connectivityManager = mockk(relaxed = true)
        savedStateHandle = mockk(relaxed = true)
        every { savedStateHandle.get<String>("q") } returns "testQuery"
        every { connectivityManager.isNetworkAvailable.value } returns true

        viewModel = SearchScreenViewModel(
            searchUseCases = getSearchResultsUseCase,
            connectivityManager = connectivityManager,
            savedStateHandle = savedStateHandle
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `onQueryChanged should trigger search and update state with results`() = runTest {
        // Given
        val query = "alex"
        val cities = listOf(
            City("alexandria", 31.2001, 29.9187, "alexandria", "")
        )
        every { connectivityManager.isNetworkAvailable.value } returns true
        coEvery { getSearchResultsUseCase(query) } returns flowOf(Result.Success(cities))

        // When
        viewModel.onQueryChanged(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(query, viewModel.state.value.query)
        assertEquals(cities, viewModel.state.value.data)
    }

    @Test
    fun `onQueryChanged should handle error`() = runTest {
        // Given
        val query = "test"
        val errorMessage = "Network Error"
        coEvery { getSearchResultsUseCase(query) } returns flowOf(Result.Error(Exception(errorMessage)))

        // When
        viewModel.onQueryChanged(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertEquals(query, viewModel.state.value.query)
        assertTrue(viewModel.state.value.data.isEmpty())

    }

    @Test
    fun `doSearch should show no network message`() = runTest {
        // Given
        val query = "test"
        every { connectivityManager.isNetworkAvailable.value } returns false

        // When
        viewModel.onQueryChanged(query)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val event = viewModel.eventFlow.replayCache.last()
        assertEquals("No network available", (event as? SearchScreenViewModel.UiEvent.ShowSnackbar)?.message)
        assertEquals(false, viewModel.loading.value)
    }
}
