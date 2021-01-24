package com.batdemir.template.data.repository

import com.batdemir.template.data.entities.db.CityModel
import com.batdemir.template.data.local.datasource.CityLocalDataSource
import com.batdemir.template.data.remote.datasource.CitiesRemoteDataSource
import com.batdemir.template.utils.performGetOperation
import javax.inject.Inject

class CitiesRepository @Inject constructor(
    private val remoteDataSource: CitiesRemoteDataSource,
    private val localDataSource: CityLocalDataSource
) {
    fun getRemote(url: String) =
        performGetOperation(networkCall = { remoteDataSource.getCities(url) })

    fun getLocal() = performGetOperation(databaseQuery = { localDataSource.get() })

    fun getLocal(id: Long) = performGetOperation(databaseQuery = { localDataSource.get(id) })

    suspend fun addLocal(model: CityModel) = localDataSource.add(model)

    suspend fun deleteLocal(model: CityModel) = localDataSource.delete(model)
}