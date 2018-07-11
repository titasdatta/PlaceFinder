package com.example.titas.placefinder.repository.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.titas.placefinder.repository.model.SavedPlace

/**
 * Created by Titas on 7/11/2018.
 */
@Database(entities = arrayOf(SavedPlace::class), version = 1)
abstract class FavoritePlacesDatabase: RoomDatabase() {
    abstract fun getFavoritesDao(): FavoritesDAO
}