package com.task.data.remote

import com.task.data.CloudsDTO
import com.task.data.MainDataDTO
import com.task.data.SysDTO
import com.task.data.WeatherForecastDataDTO
import com.task.data.WeatherDescriptionDTO
import com.task.data.WeatherResponseForecastDTO
import com.task.data.WindDTO
import domain.model.Clouds
import domain.model.MainData
import domain.model.Sys
import domain.model.WeatherForecast
import domain.model.WeatherData
import domain.model.WeatherDescription
import domain.model.Wind

fun WeatherResponseForecastDTO.toDomain(): WeatherForecast {
    return WeatherForecast(
        cod = cod,
        message = message,
        cnt = cnt,
        weatherData = list.map { it.toDomain() }
    )
}

fun WeatherForecastDataDTO.toDomain(): WeatherData {
    return WeatherData(
        timestamp = dt,
        mainData = main.toDomain(),
        weatherDescriptions = weather.map { it.toDomain() },
        clouds = clouds.toDomain(),
        wind = wind.toDomain(),
        visibility = visibility,
        probabilityOfPrecipitation = pop,
        systemData = sys.toDomain(),
        dateTimeText = dtTxt
    )
}

fun MainDataDTO.toDomain(): MainData {
    return MainData(
        temperature = temp,
        feelsLike = feelsLike,
        minTemperature = tempMin,
        maxTemperature = tempMax,
        pressure = pressure,
        seaLevel = seaLevel,
        groundLevel = grndLevel,
        humidity = humidity,
        tempKf = tempKf
    )
}

fun WeatherDescriptionDTO.toDomain(): WeatherDescription {
    return WeatherDescription(
        id = id,
        main = main,
        description = description,
        icon = icon
    )
}

fun CloudsDTO.toDomain(): Clouds {
    return Clouds(
        all = all
    )
}

fun WindDTO.toDomain(): Wind {
    return Wind(
        speed = speed,
        degree = deg,
        gust = gust
    )
}

fun SysDTO.toDomain(): Sys {
    return Sys(
        pod = pod
    )
}







