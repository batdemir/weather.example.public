package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable

data class CloudsModel(
    val all: Long
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readLong()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(all)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CloudsModel> {
        override fun createFromParcel(parcel: Parcel): CloudsModel {
            return CloudsModel(parcel)
        }

        override fun newArray(size: Int): Array<CloudsModel?> {
            return arrayOfNulls(size)
        }
    }
}