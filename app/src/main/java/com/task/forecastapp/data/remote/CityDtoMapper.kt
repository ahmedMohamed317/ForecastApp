package com.task.forecastapp.data.remote

import com.task.forecastapp.data.CityDataDTO
import domain.model.City


fun CityDataDTO.mapToDomainModel(): City {
    return City(
        name = name,
        lat = lat,
        long = lon,
        country = country,
        state = state
    )
}

fun City.mapFromDomainModel(): CityDataDTO {
    return CityDataDTO(
        name = name,
        lat = lat,
        country = country,
        state = state,
        lon = long,
        localNames = null
    )
}

fun List<CityDataDTO>.fromEntityList(): List<City> {
    return map { it.mapToDomainModel() }
}

fun List<City>.toEntityList(): List<CityDataDTO> {
    return map { it.mapFromDomainModel() }
}









