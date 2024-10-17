package com.task.forecastapp.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherResponseForecastDTO(
    @Json(name = "cod") val cod: String,
    @Json(name = "message") val message: Int,
    @Json(name = "cnt") val cnt: Int,
    @Json(name = "list") val list: List<WeatherForecastDataDTO>
) : Parcelable

@Parcelize
data class WeatherForecastDataDTO(
    @Json(name = "dt") val dt: Long,
    @Json(name = "main") val main: MainDataDTO,
    @Json(name = "weather") val weather: List<WeatherDescriptionDTO>,
    @Json(name = "clouds") val clouds: CloudsDTO,
    @Json(name = "wind") val wind: WindDTO,
    @Json(name = "visibility") val visibility: Int,
    @Json(name = "pop") val pop: Double,
    @Json(name = "sys") val sys: SysDTO,
    @Json(name = "dt_txt") val dtTxt: String
) : Parcelable

@Parcelize
data class MainDataDTO(
    @Json(name = "temp") val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "sea_level") val seaLevel: Int,
    @Json(name = "grnd_level") val grndLevel: Int,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "temp_kf") val tempKf: Double
) : Parcelable

@Parcelize
data class WeatherDescriptionDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
) : Parcelable

@Parcelize
data class CloudsDTO(
    @Json(name = "all") val all: Int
) : Parcelable

@Parcelize
data class WindDTO(
    @Json(name = "speed") val speed: Double,
    @Json(name = "deg") val deg: Int,
    @Json(name = "gust") val gust: Double
) : Parcelable

@Parcelize
data class SysDTO(
    @Json(name = "pod") val pod: String
) : Parcelable

