package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "City")
data class CityModel(
    @PrimaryKey
    @ColumnInfo(name = "city_id") val id: Long,
    @ColumnInfo(name = "city_country") val country: String,
    @ColumnInfo(name = "city_name") val name: String,
    @ColumnInfo(name = "city_state") val state: String,
    @Embedded
    val coord: CoordinateModel
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(CoordinateModel::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(country)
        parcel.writeString(name)
        parcel.writeString(state)
        parcel.writeParcelable(coord, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityModel> {
        override fun createFromParcel(parcel: Parcel): CityModel {
            return CityModel(parcel)
        }

        override fun newArray(size: Int): Array<CityModel?> {
            return arrayOfNulls(size)
        }
    }
}