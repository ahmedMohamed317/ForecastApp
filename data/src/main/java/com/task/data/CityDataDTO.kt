package com.task.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Parcelize
data class CityDataDTO(
    @Json(name = "name") val name: String,
    @Json(name = "local_names") val localNames: LocalNames?,
    @Json(name = "lat") val lat: Double,
    @Json(name = "lon") val lon: Double,
    @Json(name = "country") val country: String,
    @Json(name = "state") val state: String
) : Parcelable

@Parcelize
data class LocalNames(
    @Json(name = "de") val de: String?
) : Parcelable