package com.task.features.presentation.weatherDetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.task.features.R
import com.task.features.presentation.components.CenterContentTopAppBar
import com.task.features.presentation.components.LargeVerticalSpacer
import com.task.features.presentation.components.MediumVerticalSpacer
import com.task.features.presentation.components.SmallVerticalSpacer
import util.getIconLink
import java.util.Locale

@Composable
fun WeatherDetailsScreen(
    country: String?,
    weatherState: WeatherUiModel,
    isLoading : Boolean ,
    error : String?,
    onButtonClick : (String) -> Unit = {},
    onStartIconClicked: () -> Unit,

    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(8.dp)
    ) {

        CenterContentTopAppBar(
            title = { CustomText(text = "Current Weather", color = MaterialTheme.colorScheme.secondary, size = 20) },
            startIcon = ImageVector.vectorResource(id = R.drawable.ic_back),
            onStartIconClicked = {onStartIconClicked()},
            shouldShowBackArrow = true

        )
        MediumVerticalSpacer()
        if (country != null) {
            CustomText(
                text = country.uppercase(Locale.ROOT),
                size = 34,
            )
        }

        when {
            isLoading -> {
                CircularProgressIndicator(
                    Modifier
                        .padding(16.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                )
            }
            error != null -> {
                CustomText(
                    text = error ?: "Unknown error",
                    size = 16,
                    color = Color.Black
                )
            }
            weatherState != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomText(text = "${weatherState.temperature} ℉", size = 40)
                    SmallVerticalSpacer()

                    Image(
                        painter = rememberAsyncImagePainter(getIconLink(weatherState.icon)),
                        contentDescription = "Weather icon",
                        modifier = Modifier.size(64.dp)
                    )

                    CustomText(
                        text = weatherState.mainWeather,
                        size = 16,
                    )
                    SmallVerticalSpacer()
                    CustomText(
                        text = "Description: ${weatherState.description}",
                        size = 14,
                    )
                    MediumVerticalSpacer()
                    CustomText(
                        text = "Date: ${weatherState.date}",
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
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${weatherState.windSpeed} m/s",
                                fontSize = 16.sp,
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cloudy),
                                contentDescription = "Cloud icon",
                                modifier = Modifier.size(24.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${weatherState.cloudiness}%",
                                fontSize = 16.sp,
                            )
                        }

                    }
                    LargeVerticalSpacer()
                    Button(onClick = { country.let {
                        if (country != null) {
                            onButtonClick(country)
                        }
                    } }) {
                        Text(text = "Forecast 5 days", color = Color.White)
                    }
                }
            }
            else -> {
                CustomText(text = "No weather data available", size = 16, color = Color.Black)
            }
        }
    }
}



@Composable
fun CustomText(
    text: String,
    size: Int = 16,
    color: Color = MaterialTheme.colorScheme.secondary
) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = size.sp,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
    )
}

@Preview(showSystemUi = true , showBackground = true)
@Composable
fun WeatherDetailsScreenPreview() {
    WeatherDetailsScreen("alexandria",error = null, isLoading = false,
            weatherState = WeatherUiModel(mainWeather = "Cloudy"
        , description = "Semi cloudy at day", icon = "", date = "17/10/2024",13.5,12,12),){}
}