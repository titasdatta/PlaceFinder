package com.example.titas.placefinder.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.example.titas.placefinder.repository.PlacesRepository
import com.example.titas.placefinder.repository.model.Location
import com.example.titas.placefinder.repository.model.ResponseWrapper
import javax.inject.Inject

/**
 * Created by Titas on 7/9/2018.
 */
class PlacesViewModel @Inject constructor(private val repository: PlacesRepository): ViewModel() {

    fun getPlacesListObservable(): LiveData<ResponseWrapper>{
        return repository.getNearbyPlacesObservable()
    }

    fun searchNearbyPlacesFor(location: String, category: String){
        repository.getNearbyPlacesFor(location, category.toLowerCase())
    }
}