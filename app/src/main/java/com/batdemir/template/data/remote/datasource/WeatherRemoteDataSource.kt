package com.batdemir.template.data.remote.datasource

import com.batdemir.template.data.remote.BaseDataSource
import com.batdemir.template.data.remote.service.WeatherService
import com.batdemir.template.di.module.NetworkModule
import java.util.*
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val service: WeatherService
) : BaseDataSource() {
    suspend fun getForecast(cityId: Long) =
        getResult { service.getForecast(cityId, Locale.ROOT.language, NetworkModule.API_KEY) }

    suspend fun getWeather(cityId: Long) =
        getResult { service.getWeather(cityId, Locale.ROOT.language, NetworkModule.API_KEY) }
}