package com.example.titas.placefinder.adapter

import com.example.titas.placefinder.repository.model.Place
import com.example.titas.placefinder.repository.model.SavedPlace

/**
 * Created by Titas on 7/11/2018.
 */
interface FavoriteListener {
    fun onFavoriteClicked(place: Place)
    fun onUnFavoriteClicked(place: Place, position: Int)
}