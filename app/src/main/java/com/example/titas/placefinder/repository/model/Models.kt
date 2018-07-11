package com.example.titas.placefinder.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * Created by Titas on 7/6/2018.
 */
data class Location(@SerializedName("lat") val lat: Double, @SerializedName("lng") val lng: Double)

data class Geometry(@SerializedName("location") val location: Location)

data class OpeningHours(@SerializedName("open_now") val isOpen: Boolean)

data class Place(@SerializedName("name") val name: String,
                 @SerializedName("geometry") val geometry: Geometry,
                 @SerializedName("opening_hours") val openingHours: OpeningHours?,
                 @SerializedName("rating") val rating: Double,
                 @SerializedName("vicinity") val address: String)

data class PlacesResponse(@SerializedName("results") val placesList: ArrayList<Place>?,
                          @SerializedName("status") val status: String)


class ResponseWrapper(val placesResponse: PlacesResponse?){
    enum class Status{
        OK, FAIL
    }

    lateinit var placesList: ArrayList<Place>
    var status: Status

    init {
        if(placesResponse != null){
            placesList = placesResponse.placesList!!
            status = Status.OK
        }else {
            status = Status.FAIL
        }
    }
}