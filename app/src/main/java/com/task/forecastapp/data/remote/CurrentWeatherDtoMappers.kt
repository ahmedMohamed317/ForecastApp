package com.task.forecastapp.data.remote

import com.task.forecastapp.data.CloudsCurrentDTO
import com.task.forecastapp.data.CoordCurrentDTO
import com.task.forecastapp.data.CurrentWeatherDTO
import com.task.forecastapp.data.MainCurrentDTO
import com.task.forecastapp.data.SysCurrentDTO
import com.task.forecastapp.data.WeatherCurrentDTO
import com.task.forecastapp.data.WindCurrentDTO
import domain.model.CurrentClouds
import domain.model.CurrentCoord
import domain.model.CurrentMain
import domain.model.CurrentSys
import domain.model.CurrentWeather
import domain.model.CurrentWeatherCondition
import domain.model.CurrentWind

fun CurrentWeatherDTO.toDomain(): CurrentWeather {
    return CurrentWeather(
        coord = coord.toDomain(),
        weather = weather.map { it.toDomain() },
        base = base,
        main = main.toDomain(),
        visibility = visibility,
        wind = wind.toDomain(),
        clouds = clouds.toDomain(),
        dt = dt,
        sys = sys.toDomain(),
        timezone = timezone,
        id = id,
        name = name,
        cod = cod
    )
}

fun CoordCurrentDTO.toDomain(): CurrentCoord {
    return CurrentCoord(lon = lon, lat = lat)
}

fun WeatherCurrentDTO.toDomain(): CurrentWeatherCondition {
    return CurrentWeatherCondition(
        id = id,
        main = main,
        description = description,
        icon = icon
    )
}

fun MainCurrentDTO.toDomain(): CurrentMain {
    return CurrentMain(
        temp = temp,
        feelsLike = feelsLike,
        tempMin = tempMin,
        tempMax = tempMax,
        pressure = pressure,
        humidity = humidity,
        seaLevel = seaLevel,
        groundLevel = groundLevel
    )
}

fun WindCurrentDTO.toDomain(): CurrentWind {
    return CurrentWind(
        speed = speed,
        deg = deg
    )
}

fun CloudsCurrentDTO.toDomain(): CurrentClouds {
    return CurrentClouds(all = all)
}

fun SysCurrentDTO.toDomain(): CurrentSys {
    return CurrentSys(
        type = type,
        id = id,
        country = country,
        sunrise = sunrise,
        sunset = sunset
    )
}
