package com.example.titas.placefinder.repository.network

import com.example.titas.placefinder.common.Constants
import com.example.titas.placefinder.repository.model.PlacesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Titas on 7/6/2018.
 */
interface PlacesService {
    @GET("nearbysearch/json?key=${Constants.MAPS_REQUEST_API_KEY}")
    fun getNearbyPlaces(@Query("location") location: String,
                        @Query("type") type: String,
                        @Query("rankby") rankBy: String = "distance"): Call<PlacesResponse>
}