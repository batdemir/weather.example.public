package com.batdemir.template.di.module

import com.batdemir.template.data.local.datasource.CityLocalDataSource
import com.batdemir.template.data.remote.datasource.CitiesRemoteDataSource
import com.batdemir.template.data.remote.datasource.WeatherRemoteDataSource
import com.batdemir.template.data.repository.CitiesRepository
import com.batdemir.template.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRepositoryCities(
        remoteDataSource: CitiesRemoteDataSource,
        localDataSource: CityLocalDataSource
    ) =
        CitiesRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideRepositoryWeather(
        remoteDataSource: WeatherRemoteDataSource,
    ) =
        WeatherRepository(remoteDataSource)
}