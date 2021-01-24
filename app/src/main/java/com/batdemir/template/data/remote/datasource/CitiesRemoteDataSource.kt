package com.batdemir.template.data.remote.datasource

import com.batdemir.template.data.remote.BaseDataSource
import com.batdemir.template.data.remote.service.CitiesService
import javax.inject.Inject

class CitiesRemoteDataSource @Inject constructor(
    private val service: CitiesService
) : BaseDataSource() {
    suspend fun getCities(url: String) = getResult { service.getCities(url) }
}