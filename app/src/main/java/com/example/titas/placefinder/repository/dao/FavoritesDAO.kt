package com.example.titas.placefinder.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.titas.placefinder.repository.model.SavedPlace


/**
 * Created by Titas on 7/11/2018.
 */
@Dao
interface FavoritesDAO {
    @Insert(onConflict = REPLACE)
    fun insertPlace(savedPlace: SavedPlace)

    @Query("DELETE FROM savedPlaces WHERE lower(name)=lower(:name)")
    fun removePlace(name: String)

    @Query("SELECT * FROM savedPlaces limit 10")
    fun getSavedPlaces(): LiveData<List<SavedPlace>>
}