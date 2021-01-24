package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable

data class ResponseForecastModel(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ListModel>,
    val city: CityModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong(),
        parcel.createTypedArrayList(ListModel)!!,
        parcel.readParcelable(CityModel::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cod)
        parcel.writeLong(message)
        parcel.writeLong(cnt)
        parcel.writeTypedList(list)
        parcel.writeParcelable(city, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseForecastModel> {
        override fun createFromParcel(parcel: Parcel): ResponseForecastModel {
            return ResponseForecastModel(parcel)
        }

        override fun newArray(size: Int): Array<ResponseForecastModel?> {
            return arrayOfNulls(size)
        }
    }
}