package com.batdemir.template.di.module

import com.batdemir.template.BuildConfig
import com.batdemir.template.data.remote.datasource.CitiesRemoteDataSource
import com.batdemir.template.data.remote.datasource.WeatherRemoteDataSource
import com.batdemir.template.data.remote.service.CitiesService
import com.batdemir.template.data.remote.service.WeatherService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    private const val BASE_URL: String = "http://api.openweathermap.org/"
    const val API_KEY: String = ""

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient
            .Builder()
        if (BuildConfig.DEBUG)
            okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    //////////////////////////////////////////////////////

    @Singleton
    @Provides
    fun provideServiceCities(retrofit: Retrofit): CitiesService =
        retrofit.create(CitiesService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSourceCities(service: CitiesService) =
        CitiesRemoteDataSource(service)

    //////////////////////////////////////////////////////

    //////////////////////////////////////////////////////

    @Singleton
    @Provides
    fun provideServiceWeather(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSourceWeather(service: WeatherService) =
        WeatherRemoteDataSource(service)

    //////////////////////////////////////////////////////
}