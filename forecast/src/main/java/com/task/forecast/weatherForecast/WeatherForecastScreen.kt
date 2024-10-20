package com.task.forecast.weatherForecast

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.task.core.R
import com.task.features.presentation.components.CenterContentTopAppBar
import com.task.features.presentation.components.MediumVerticalSpacer
import com.task.features.presentation.components.SmallVerticalSpacer
import com.task.features.presentation.weatherDetails.CustomText
import com.task.features.presentation.weatherForecast.ForecastUiModel
import com.task.features.presentation.weatherForecast.WeatherForecastScreenState
import util.getIconLink
import com.task.forecastutilis.toCelsius
import com.task.forecastutilis.toFormattedDate


@Composable
fun WeatherForecastScreen(
    uiState: WeatherForecastScreenState,
    onBackIconClicked: () -> Unit ,
    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {

        CenterContentTopAppBar(
            title = {
                CustomText(
                    text = "Weather Forecast",
                    color = MaterialTheme.colorScheme.secondary,
                    size = 20
                )
            },
            startIcon = ImageVector.vectorResource(id = R.drawable.ic_back),
            onStartIconClicked = {onBackIconClicked()},
            shouldShowBackArrow = true
            )
        MediumVerticalSpacer()
        LazyRow {
            val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Gray, Color.Cyan, Color.Magenta)
            itemsIndexed(uiState.weather ?: emptyList()) { index, item ->
                val backgroundColor = colors[index % colors.size]
                Card(
                    modifier = Modifier
                        .width(260.dp)
                        .height(320.dp)
                        .padding(16.dp)
                    ,
                    colors = CardDefaults.cardColors(containerColor = backgroundColor)
                    ,shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        CustomText(text = "${item.temperature} ℉", size = 40, color = Color.White)

                        Image(
                            painter = rememberAsyncImagePainter(getIconLink(item.icon)),
                            contentDescription = "Weather icon",
                            modifier = Modifier
                                .size(44.dp)
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                        SmallVerticalSpacer()
                        CustomText(text = item.mainWeather, size = 14, color = Color.White)
                        CustomText(
                            text = "Description: ${item.description}",
                            size = 14,
                            color = Color.White
                        )
                        CustomText(
                            text = "Date: ${item.date}",
                            color = Color.White,
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_wind),
                                    contentDescription = "Wind icon",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${item.windSpeed} m/s",
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_cloudy),
                                    contentDescription = "Cloud icon",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${item.cloudiness}%",
                                    fontSize = 16.sp,
                                    color = Color.White
                                )
                            }

                        }
                    }
                }
            }
        }



    }
}
@Preview(showSystemUi = true , showBackground = true)
@Composable
fun WeatherForecastScreenPreview() {
    WeatherForecastScreen(
        WeatherForecastScreenState(isLoading = false,
            error = null, weather = listOf(
                ForecastUiModel("cloudy",
                "semi cloudy",
                "", "1-1-2001",
                0.0, 0,
                207),
                ForecastUiModel("cloudy",
                    "semi cloudy",
                    "", "1-1-2001",
                    0.0, 0,
                    207),
                ForecastUiModel("cloudy",
                    "semi cloudy",
                    "", "1-1-2001",
                    0.0, 0,
                    207)
            )
        )
    ){}
}