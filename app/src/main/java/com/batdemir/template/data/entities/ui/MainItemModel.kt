package com.batdemir.template.data.entities.ui

data class MainItemModel(
    val id: Long,
    val name: String,
    var selected: Boolean = false,
    val mainCurrentDayHoursModels: List<MainCurrentDayHoursModel>?,
    val mainWeeklyModels: List<MainWeeklyModel>?
)

data class MainCurrentDayHoursModel(
    val id: Long,
    val weather: Weather,
    val hour: String,
    val mainCurrentDetailModel: MainCurrentDetailModel?,
    val mainCurrentItemModel: MainCurrentItemModel?,
    var selected: Boolean = false
)

data class MainCurrentItemModel(
    val weather: Weather?,
    val temp: String?,
    val description: String?,
    val date: String?,
    val maxTemp: String?,
    val minTemp: String?
)

data class MainCurrentDetailModel(
    val windy: String,
    val visibility: String,
    val moisture: String,
    val uv: String
)

data class MainWeeklyModel(
    val id: Long?,
    val day: String?,
    val weather: Weather?,
    val maxTemp: String?,
    val minTemp: String?
)