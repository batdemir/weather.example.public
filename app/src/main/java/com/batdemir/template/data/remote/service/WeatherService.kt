package com.batdemir.template.data.remote.service

import com.batdemir.template.data.entities.db.ResponseForecastModel
import com.batdemir.template.data.entities.db.ResponseWeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/forecast")
    suspend fun getForecast(
        @Query("id") cityId: Long,
        @Query("lang") lang: String,
        @Query("appid") key: String
    ): Response<ResponseForecastModel>

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("id") cityId: Long,
        @Query("lang") lang: String,
        @Query("appid") key: String
    ): Response<ResponseWeatherModel>
}