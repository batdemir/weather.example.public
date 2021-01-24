package com.batdemir.template.data.local.datasource

import com.batdemir.template.data.entities.db.CityModel
import com.batdemir.template.data.local.dao.CityDao
import javax.inject.Inject

class CityLocalDataSource @Inject constructor(
    private val dao: CityDao
) {
    fun get() = dao.get()

    fun get(id: Long) = dao.get(id)

    suspend fun add(model: CityModel) = dao.add(model)

    suspend fun delete(model: CityModel) = dao.delete(model)

    suspend fun update(model: CityModel) = dao.update(model)
}