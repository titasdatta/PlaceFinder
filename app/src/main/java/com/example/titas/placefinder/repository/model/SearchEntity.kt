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