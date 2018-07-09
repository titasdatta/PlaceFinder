package com.example.titas.placefinder.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.titas.placefinder.repository.model.PlacesResponse
import com.example.titas.placefinder.repository.model.ResponseWrapper
import com.example.titas.placefinder.repository.network.PlacesService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Titas on 7/6/2018.
 */
@Singleton
class PlacesRepository @Inject constructor(private val placesService: PlacesService) {
    private val placesResponseData: MutableLiveData<ResponseWrapper> = MutableLiveData()

    fun getNearbyPlacesFor(location: String, type: String) {
        val call: Call<PlacesResponse> = placesService.getNearbyPlaces(location, type)
        call.enqueue(object : Callback<PlacesResponse> {
            override fun onFailure(call: Call<PlacesResponse>?, t: Throwable?) {
                val responseWrapper = ResponseWrapper(null)
                placesResponseData.postValue(responseWrapper)
            }

            override fun onResponse(call: Call<PlacesResponse>?, response: Response<PlacesResponse>?) {
                val responseWrapper = ResponseWrapper(response?.body())
                placesResponseData.postValue(responseWrapper)
            }

        })
    }

    fun getNearbyPlacesObservable(): LiveData<ResponseWrapper> = placesResponseData
}