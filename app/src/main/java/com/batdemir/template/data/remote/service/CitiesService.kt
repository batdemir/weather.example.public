package com.batdemir.template.data.remote.service

import com.batdemir.template.data.entities.db.CityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CitiesService {
    @GET
    suspend fun getCities(@Url url: String): Response<List<CityModel>>
}