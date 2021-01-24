package com.batdemir.template.data.entities.ui

import android.os.Parcel
import android.os.Parcelable
import com.batdemir.template.data.entities.db.CityModel
import com.batdemir.template.utils.DateFormat
import com.batdemir.template.utils.toString
import java.util.*

data class CitiesItemModel(
    val id: String?,
    val no: Int?,
    val weather: Weather?,
    val city: CityModel?,
    val date: Long?,
    val temp: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Weather::class.java.classLoader) as? Weather,
        parcel.readValue(CityModel::class.java.classLoader) as? CityModel,
        parcel.readLong(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(no)
        parcel.writeValue(weather)
        parcel.writeValue(city)
        parcel.writeLong(date ?: 0)
        parcel.writeString(temp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CitiesItemModel> {
        override fun createFromParcel(parcel: Parcel): CitiesItemModel {
            return CitiesItemModel(parcel)
        }

        override fun newArray(size: Int): Array<CitiesItemModel?> {
            return arrayOfNulls(size)
        }
    }

    fun getDateStr(): String {
        val date = Date(date?.times(1000L) ?: 0)
        return date.toString(DateFormat.SHOW_DATE_FORMAT)
    }
}