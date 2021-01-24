package com.batdemir.template.data.repository

import com.batdemir.template.data.remote.datasource.WeatherRemoteDataSource
import com.batdemir.template.utils.performGetOperation
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) {
    fun getForecast(cityId: Long) =
        performGetOperation(networkCall = { remoteDataSource.getForecast(cityId) })

    fun getWeather(cityId: Long) =
        performGetOperation(networkCall = { remoteDataSource.getWeather(cityId) })
}