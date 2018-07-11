package com.example.titas.placefinder.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.os.AsyncTask
import com.example.titas.placefinder.common.runOnIoThread
import com.example.titas.placefinder.repository.dao.SearchDAO
import com.example.titas.placefinder.repository.model.SearchData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Titas on 7/6/2018.
 */
@Singleton
class SearchRepository @Inject constructor(private val searchDAO: SearchDAO) {

    fun getRecentSearches(): LiveData<List<SearchData>>{
        return searchDAO.getRecentSearches()
    }

    fun insertSearch(searchData: SearchData){
        runOnIoThread {
            if(searchDAO.getSingleSearchRecord(searchData.searchTitle) == null) {
                searchDAO.insertSearch(searchData)
            } else {
                searchDAO.updateSearch(searchData.searchTitle, searchData.searchTime)
            }
        }
    }
}