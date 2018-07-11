package com.example.titas.placefinder.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.example.titas.placefinder.repository.FavoritesRepository
import com.example.titas.placefinder.repository.PlacesRepository
import com.example.titas.placefinder.repository.model.*
import javax.inject.Inject

/**
 * Created by Titas on 7/9/2018.
 */
class PlacesViewModel @Inject constructor(private val placesRepository: PlacesRepository,
                                          private val favoritesRepository: FavoritesRepository): ViewModel() {

    fun getPlacesListObservable(): LiveData<ResponseWrapper>{
        return placesRepository.getNearbyPlacesObservable()
    }

    fun searchNearbyPlacesFor(location: String, category: String){
        var categoryString: String = ""
        if(category.split(" ").isNotEmpty()){
            val categoryWords = category.split(" ")
            categoryString = categoryWords[0]
            for (i in 1..categoryWords.size-1){
                categoryString += "_${categoryWords[i]}"
            }
        } else {
            categoryString = category
        }
        placesRepository.getNearbyPlacesFor(location, categoryString.toLowerCase())
    }

    private fun convertSavedPlaceList(savedPlaceList: List<SavedPlace>): List<Place>{
        val listOfPlace = ArrayList<Place>()
        savedPlaceList.forEach {
            val place = Place(it.name, Geometry(Location(it.latitude, it.longitude)), OpeningHours(it.isOpen ?: false),
                    it.rating, it.address)
            listOfPlace.add(place)
        }
        return listOfPlace
    }

    fun getFavoritesObservable(): LiveData<List<Place>> {
        val originalList = favoritesRepository.getFavoritesList()
        val transformationFunction: (savedPlaceList: List<SavedPlace>) -> List<Place> = ::convertSavedPlaceList
        val transformedList: LiveData<List<Place>> = Transformations.map(originalList, transformationFunction)
        return transformedList
    }


    fun favoritePlace(place: Place) {
        val savedPlace = SavedPlace(name = place.name, address = place.address,
                isOpen = place.openingHours?.isOpen, rating = place.rating, latitude = place.geometry.location.lat,
                longitude = place.geometry.location.lng)
        favoritesRepository.favoritePlace(savedPlace)
    }

    fun unfavoritePlace(place: Place) {
        val savedPlace = SavedPlace(name = place.name, address = place.address,
                isOpen = place.openingHours?.isOpen, rating = place.rating, latitude = place.geometry.location.lat,
                longitude = place.geometry.location.lng)
        favoritesRepository.unfavoritePlace(savedPlace)
    }
}