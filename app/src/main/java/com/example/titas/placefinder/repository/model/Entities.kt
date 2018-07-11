package com.example.titas.placefinder.repository.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Titas on 7/6/2018.
 */

@Entity(tableName = "searchData")
data class SearchData(@PrimaryKey(autoGenerate = true) var id: Long=0,
                      @ColumnInfo(name = "searchTitle") var searchTitle: String,
                      @ColumnInfo(name = "searchTime") var searchTime: Long)

@Entity(tableName = "savedPlaces")
data class SavedPlace(@PrimaryKey(autoGenerate = true) var id: Long=0,
                      @ColumnInfo(name = "name") var name: String,
                      @ColumnInfo(name = "address") var address: String,
                      @ColumnInfo(name = "isOpen") var isOpen: Boolean,
                      @ColumnInfo(name = "rating") var rating: Double,
                      @ColumnInfo(name = "latitude") var latitude: Double,
                      @ColumnInfo(name = "longitude") var longitude: Double)