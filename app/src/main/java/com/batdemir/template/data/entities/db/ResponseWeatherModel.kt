package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable

data class ResponseWeatherModel(
    val coord: CoordinateModel,
    val weather: List<WeatherModel>,
    val base: String,
    val main: MainModel,
    val visibility: Long,
    val wind: WindModel,
    val clouds: CloudsModel,
    val dt: Long,
    val sys: SysModel,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(CoordinateModel::class.java.classLoader)!!,
        parcel.createTypedArrayList(WeatherModel)!!,
        parcel.readString()!!,
        parcel.readParcelable(MainModel::class.java.classLoader)!!,
        parcel.readLong(),
        parcel.readParcelable(WindModel::class.java.classLoader)!!,
        parcel.readParcelable(CloudsModel::class.java.classLoader)!!,
        parcel.readLong(),
        parcel.readParcelable(SysModel::class.java.classLoader)!!,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(coord, flags)
        parcel.writeTypedList(weather)
        parcel.writeString(base)
        parcel.writeParcelable(main, flags)
        parcel.writeLong(visibility)
        parcel.writeParcelable(wind, flags)
        parcel.writeParcelable(clouds, flags)
        parcel.writeLong(dt)
        parcel.writeParcelable(sys, flags)
        parcel.writeLong(timezone)
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeLong(cod)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseWeatherModel> {
        override fun createFromParcel(parcel: Parcel): ResponseWeatherModel {
            return ResponseWeatherModel(parcel)
        }

        override fun newArray(size: Int): Array<ResponseWeatherModel?> {
            return arrayOfNulls(size)
        }
    }
}