package com.task.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentWeatherDTO(
    @Json(name = "coord") val coord: CoordCurrentDTO,
    @Json(name = "weather") val weather: List<WeatherCurrentDTO>,
    @Json(name = "base") val base: String,
    @Json(name = "main") val main: MainCurrentDTO,
    @Json(name = "visibility") val visibility: Int,
    @Json(name = "wind") val wind: WindCurrentDTO,
    @Json(name = "clouds") val clouds: CloudsCurrentDTO,
    @Json(name = "dt") val dt: Long,
    @Json(name = "sys") val sys: SysCurrentDTO,
    @Json(name = "timezone") val timezone: Int,
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "cod") val cod: Int
) : Parcelable

@Parcelize
data class CoordCurrentDTO(
    @Json(name = "lon") val lon: Double,
    @Json(name = "lat") val lat: Double
) : Parcelable

@Parcelize
data class WeatherCurrentDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
) : Parcelable

@Parcelize
data class MainCurrentDTO(
    @Json(name = "temp") val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "sea_level") val seaLevel: Int?,
    @Json(name = "grnd_level") val groundLevel: Int?
) : Parcelable

@Parcelize
data class WindCurrentDTO(
    @Json(name = "speed") val speed: Double,
    @Json(name = "deg") val deg: Int
) : Parcelable

@Parcelize
data class CloudsCurrentDTO(
    @Json(name = "all") val all: Int
) : Parcelable

@Parcelize
data class SysCurrentDTO(
    @Json(name = "type") val type: Int?,
    @Json(name = "id") val id: Int?,
    @Json(name = "country") val country: String,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long
) : Parcelable
