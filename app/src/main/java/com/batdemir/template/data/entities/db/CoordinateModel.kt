package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "Coordinate")
data class CoordinateModel(
    @ColumnInfo(name = "coordinate_lat") val lat: Double,
    @ColumnInfo(name = "coordinate_lon") val lon: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoordinateModel> {
        override fun createFromParcel(parcel: Parcel): CoordinateModel {
            return CoordinateModel(parcel)
        }

        override fun newArray(size: Int): Array<CoordinateModel?> {
            return arrayOfNulls(size)
        }
    }
}