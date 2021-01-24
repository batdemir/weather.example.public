package com.batdemir.template.data.entities.ui

import com.batdemir.template.R

enum class Weather(val dayIcon: String, val nightIcon: String, val icon: Int) {
    CLEAR_SKY("01d", "01n", R.drawable.ic_sunny_small),
    FEW_CLOUDS("02d", "02n", R.drawable.ic_cloudy_sunny_small),
    SCATTERED_CLOUDS("03d", "03n", R.drawable.ic_cloudy_small),
    BROKEN_CLOUDS("04d", "04n", R.drawable.ic_rainy_small),
    SHOWER_RAIN("09d", "09n", R.drawable.ic_rainy_small),
    RAIN("10d", "10n", R.drawable.ic_rainy_small),
    THUNDERSTROM("11d", "11n", R.drawable.ic_rainy_small),
    SNOW("13d", "13n", R.drawable.ic_rainy_small),
    MIST("50d", "50n", R.drawable.ic_rainy_small),
}