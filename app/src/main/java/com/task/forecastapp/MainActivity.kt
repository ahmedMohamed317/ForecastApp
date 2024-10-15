package com.task.forecastapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.task.features.presentation.components.DisposableEffectWithLifeCycle
import com.task.features.presentation.search.SearchScreen
import com.task.features.presentation.weatherDetails.WeatherDetailsScreen
import com.task.forecastapp.ui.theme.ForecastAppTheme
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
            ForecastAppTheme {
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
                navController.navigate("search/${it.lat}/${it.long}")
            }
        }
        composable(

            route = "search/{lat}/{long}",
            arguments = listOf(
                navArgument("lat") {
                    type = NavType.FloatType
                },
                navArgument("long") {
                    type = NavType.FloatType
                }
            )
        ) { backStackEntry ->
            val lat = backStackEntry.arguments?.getFloat("lat")
            val long = backStackEntry.arguments?.getFloat("long")
            WeatherDetailsScreen(lat = lat, long = long)
        }
    }
}
