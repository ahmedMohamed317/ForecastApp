package domain.model

data class CurrentWeather(
    val coord: CurrentCoord,
    val weather: List<CurrentWeatherCondition>,
    val base: String,
    val main: CurrentMain,
    val visibility: Int,
    val wind: CurrentWind,
    val clouds: CurrentClouds,
    val dt: Long,
    val sys: CurrentSys,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

data class CurrentCoord(
    val lon: Double,
    val lat: Double
)

data class CurrentWeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class CurrentMain(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val seaLevel: Int?,
    val groundLevel: Int?
)

data class CurrentWind(
    val speed: Double,
    val deg: Int
)

data class CurrentClouds(
    val all: Int
)

data class CurrentSys(
    val type: Int?,
    val id: Int?,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
