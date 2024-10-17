package domain.model


data class WeatherForecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val weatherData: List<WeatherData>
)

data class WeatherData(
    val timestamp: Long,
    val mainData: MainData,
    val weatherDescriptions: List<WeatherDescription>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val probabilityOfPrecipitation: Double,
    val systemData: Sys,
    val dateTimeText: String
)

data class MainData(
    val temperature: Double,
    val feelsLike: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val pressure: Int,
    val seaLevel: Int,
    val groundLevel: Int,
    val humidity: Int,
    val tempKf: Double
)

data class WeatherDescription(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Clouds(val all: Int)

data class Wind(
    val speed: Double,
    val degree: Int,
    val gust: Double
)

data class Sys(val pod: String)

