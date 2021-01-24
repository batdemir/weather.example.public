package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable

data class WindModel(
    val speed: Double,
    val deg: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(speed)
        parcel.writeLong(deg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WindModel> {
        override fun createFromParcel(parcel: Parcel): WindModel {
            return WindModel(parcel)
        }

        override fun newArray(size: Int): Array<WindModel?> {
            return arrayOfNulls(size)
        }
    }
}