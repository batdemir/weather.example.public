package com.batdemir.template.data.entities.db

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class MainModel(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Long,
    @SerializedName("sea_level")
    val seaLevel: Long,
    @SerializedName("grnd_level")
    val grndLevel: Long,
    val humidity: Long,
    @SerializedName("temp_kf")
    val tempKf: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(temp)
        parcel.writeDouble(feelsLike)
        parcel.writeDouble(tempMin)
        parcel.writeDouble(tempMax)
        parcel.writeLong(pressure)
        parcel.writeLong(seaLevel)
        parcel.writeLong(grndLevel)
        parcel.writeLong(humidity)
        parcel.writeDouble(tempKf)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainModel> {
        override fun createFromParcel(parcel: Parcel): MainModel {
            return MainModel(parcel)
        }

        override fun newArray(size: Int): Array<MainModel?> {
            return arrayOfNulls(size)
        }
    }
}