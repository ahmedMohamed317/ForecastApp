package domain.usecase

import domain.Result
import domain.model.City
import domain.repository.AppRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetSearchResultsUseCaseTest {

    private lateinit var repo: AppRepository
    private lateinit var getSearchResultUseCase: GetSearchResultsUseCase

    @Before
    fun setup() {
        repo = mockk(relaxed = true, relaxUnitFun = true)
        getSearchResultUseCase = GetSearchResultsUseCase(repo)
    }

    @Test
    fun returnListFromRepoWhenInvokeUseCaseSortedByCityName() = runTest {
        // Given
        val query = "a"
        val unSortedCityList = listOf(
            City(country = "assiut",lat = 31.2044,long = 29.9792 ,name = "assiut",state = ""),
            City(country = "alexandria",lat = 30.0444,long = 31.2357 ,name = "alexandria",state = ""))

        val sortedCityList = listOf(
            City(country = "alexandria",lat = 30.0444,long = 31.2357 ,name = "alexandria",state = ""),
            City(country = "assiut",lat = 31.2044,long = 29.9792 ,name = "assiut",state = ""))

        // Mock repository behavior
        coEvery { repo.getSearchResults(query)  } returns flowOf(Result.Success(unSortedCityList))

        // When
        val resultFlow = getSearchResultUseCase(query)

        // Then
        resultFlow.collect { result ->
            val expected = Result.Success(sortedCityList)

            // Print both values
            println("Expected: $expected")
            println("Actual: $result")

            // Assert the values
            assertEquals(expected, result)
        }
    }
}