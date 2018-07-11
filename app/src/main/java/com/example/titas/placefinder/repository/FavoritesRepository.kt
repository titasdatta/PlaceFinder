package com.example.titas.placefinder.repository

import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.example.titas.placefinder.repository.dao.FavoritesDAO
import com.example.titas.placefinder.repository.model.SavedPlace
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Titas on 7/11/2018.
 */
@Singleton
class FavoritesRepository @Inject constructor(private val favoritesDAO: FavoritesDAO) {

    fun getFavoritesList(): LiveData<List<SavedPlace>> = favoritesDAO.getSavedPlaces()

    fun favoritePlace(savedPlace: SavedPlace) {
        AsyncTask.execute {
            favoritesDAO.insertPlace(savedPlace)
        }
    }

    fun unfavoritePlace(savedPlace: SavedPlace) {
        AsyncTask.execute {
            favoritesDAO.removePlace(savedPlace.name)
        }
    }
}