package com.batdemir.template.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.batdemir.template.data.entities.db.CityModel

@Dao
interface CityDao {
    @Query("SELECT * FROM City")
    fun get(): LiveData<List<CityModel>>

    @Query("SELECT * FROM City WHERE city_id = :id")
    fun get(id: Long): LiveData<List<CityModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(model: CityModel)

    @Delete
    suspend fun delete(model: CityModel)

    @Update
    suspend fun update(model: CityModel)
}