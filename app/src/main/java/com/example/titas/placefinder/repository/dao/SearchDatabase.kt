package com.example.titas.placefinder.repository.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.titas.placefinder.repository.model.SearchData

/**
 * Created by Titas on 7/6/2018.
 */
@Database(entities = arrayOf(SearchData::class), version = 1)
abstract class SearchDatabase: RoomDatabase() {
    abstract fun getSearchDAO(): SearchDAO
}