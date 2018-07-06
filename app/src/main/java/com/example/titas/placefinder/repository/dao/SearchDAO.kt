package com.example.titas.placefinder.repository.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.example.titas.placefinder.repository.model.SearchData

/**
 * Created by Titas on 7/6/2018.
 */
@Dao
interface SearchDAO {

    @Query("SELECT * FROM searchData ORDER BY searchTime DESC LIMIT 10")
    fun getRecentSearches(): LiveData<List<SearchData>>

    @Insert(onConflict = REPLACE)
    fun insertSearch(searchData: SearchData)
}