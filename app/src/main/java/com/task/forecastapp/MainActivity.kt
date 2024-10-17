package com.task.forecastapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import theme.WeatherAppTheme
import com.task.features.presentation.components.DisposableEffectWithLifeCycle
import com.task.features.presentation.search.SearchScreen
import com.task.features.presentation.weatherDetails.CurrentWeatherDetailsViewModel
import com.task.features.presentation.weatherDetails.WeatherDetailsScreen
import com.task.features.presentation.weatherForecast.WeatherForecastScreen
import com.task.features.presentation.weatherForecast.WeatherForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import util.ConnectivityManager
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DisposableEffectWithLifeCycle(
                onStart = {
                    connectivityManager.registerConnectionObserver(this)
                },
                onDestroy = {
                    connectivityManager.registerConnectionObserver(this)
                }
            )
            WeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ForecastApp(paddingValues = innerPadding)
                }
            }
        }
    }
}
@Composable
private fun ForecastApp( modifier: Modifier = Modifier,paddingValues:PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "search") {

        composable(route = "search") {
            SearchScreen(paddingValues = paddingValues){
                try {
                navController.navigate("search/${it.name.trim().split(" ").first()}")}
                catch (e:Exception){
                    Log.d("MainActivity", "ForecastAppException: ${e.message}")
                }
            }
        }
        composable(

            route = "search/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: CurrentWeatherDetailsViewModel = hiltViewModel()
            val country by viewModel.query.collectAsState()
            val uiState by viewModel.uiState.collectAsState()
            WeatherDetailsScreen(country,uiState){
                navController.navigate("details/${country?.trim()?.split(" ")?.first()}")
            }
        }
        composable(
            route = "details/{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                }
            )
        ) {
            val viewModel: WeatherForecastViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            WeatherForecastScreen(uiState = uiState )
        }
    }
}
