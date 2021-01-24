package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable

data class ListModel(
    val dt: Long,
    val main: MainModel,
    val weather: List<WeatherModel>,
    val clouds: CloudsModel,
    val wind: WindModel,
    val visibility: Long,
    val pop: Double,
    val sys: SysModel,
    val dt_txt: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readParcelable(MainModel::class.java.classLoader)!!,
        parcel.createTypedArrayList(WeatherModel)!!,
        parcel.readParcelable(CloudsModel::class.java.classLoader)!!,
        parcel.readParcelable(WindModel::class.java.classLoader)!!,
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readParcelable(SysModel::class.java.classLoader)!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(dt)
        parcel.writeParcelable(main, flags)
        parcel.writeTypedList(weather)
        parcel.writeParcelable(clouds, flags)
        parcel.writeParcelable(wind, flags)
        parcel.writeLong(visibility)
        parcel.writeDouble(pop)
        parcel.writeParcelable(sys, flags)
        parcel.writeString(dt_txt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListModel> {
        override fun createFromParcel(parcel: Parcel): ListModel {
            return ListModel(parcel)
        }

        override fun newArray(size: Int): Array<ListModel?> {
            return arrayOfNulls(size)
        }
    }
}