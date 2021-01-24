package com.batdemir.template.utils

import java.util.*

fun Double.fahrenheitToCelsius(): Double {
    return ((this - 32) * 5) / 9
}

fun Double.fahrenheitToCelsiusString(locale: Locale = Locale.ROOT): String {
    return String.format(locale, "%.0f°C", fahrenheitToCelsius())
}

fun Double.kelvinToCelsius(): Double {
    return this - 273.15
}

fun Double.kelvinToCelsiusString(locale: Locale = Locale.ROOT): String {
    return String.format(locale, "%.0f°C", kelvinToCelsius())
}