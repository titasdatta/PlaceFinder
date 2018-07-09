package com.example.titas.placefinder.common

/**
 * Created by Titas on 7/6/2018.
 */
class Constants{
    companion object {
        const val MAPS_REQUEST_API_KEY = "AIzaSyBCA4pLkC7BNwEYe_OuHKIPo7Vz1OSGcNg"
        const val MAPS_REQUEST_BASE_URL = "https://maps.googleapis.com/maps/api/place/"
        const val LOCATION_REFRESH_TIME: Long = 5*60*1000
        const val LOCATION_REFRESH_DISTANCE: Float = 5000f
        const val PERMISSION_REQUEST_CODE: Int = 1001
    }
}