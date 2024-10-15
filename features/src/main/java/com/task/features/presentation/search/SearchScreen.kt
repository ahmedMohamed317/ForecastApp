package com.task.features.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.features.R
import com.task.features.presentation.components.CenterContentTopAppBar
import com.task.features.presentation.components.MediumVerticalSpacer
import com.task.features.presentation.components.TinyVerticalSpacer
import domain.model.City

@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel(),
    paddingValues: PaddingValues,
    onRecommendationClicked: (location: City) -> Unit
) {
    val state = viewModel.state.value

    Column(modifier = Modifier.padding(paddingValues)) {
        CenterContentTopAppBar(
            title = { Text("Search", color = Color.Black) },
            startIcon = ImageVector.vectorResource(id = R.drawable.ic_back),
            onStartIconClicked = {},
        )
        MediumVerticalSpacer()
        Content(
            searchFieldValue = state.query,
            onSearchFieldValueChanged = { newValue ->
                viewModel.onQueryChanged(newValue)
            },
            recommendationResult = state.data,
            onRecommendationClicked = onRecommendationClicked,
            onSearchFieldValueCleared = viewModel::clearQuery,
            isClearSearchQueryVisible = state.query.isNotBlank()
        )
    }
}

@Composable
fun Content(
    searchFieldValue: String,
    onSearchFieldValueChanged: (String) -> Unit,
    recommendationResult: List<City>?,
    onRecommendationClicked: (City) -> Unit,
    onSearchFieldValueCleared: () -> Unit,
    isClearSearchQueryVisible: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        OutlinedTextField(
            value = searchFieldValue,
            onValueChange = onSearchFieldValueChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Enter your city name") },
            singleLine = true,
            trailingIcon = {
                if (isClearSearchQueryVisible) {
                    IconButton(onClick = onSearchFieldValueCleared) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                            contentDescription = stringResource(id = R.string.content_description_none)
                        )
                    }
                }
            }
        )
        TinyVerticalSpacer()

        recommendationResult?.let { cities ->
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(cities) { city ->
                    SimpleSearchRecommendation(
                        recommendation = city,
                        onRecommendationClicked = onRecommendationClicked
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleSearchRecommendation(
    recommendation: City,
    onRecommendationClicked: (City) -> Unit
) {
    Surface(
        onClick = { onRecommendationClicked(recommendation) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.height(48.dp)) {
            Text(
                text = recommendation.name,
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun SearchScreenPreview() {
    val sampleData = listOf(City(name = "Sample City", country = "Sample Country", lat = 0.0, long = 0.0, state = "Sample State"))

    Content(
        searchFieldValue = "",
        onSearchFieldValueChanged = {},
        recommendationResult = sampleData,
        onRecommendationClicked = {},
        onSearchFieldValueCleared = {},
        isClearSearchQueryVisible = true
    )
}
