package com.example.titas.placefinder.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.titas.placefinder.repository.dao.SearchDAO
import com.example.titas.placefinder.repository.model.SearchData
import javax.inject.Inject

/**
 * Created by Titas on 7/6/2018.
 */
class SearchRepository @Inject constructor(private val searchDAO: SearchDAO) {

    fun getRecentSearches(): LiveData<List<SearchData>>{
        return searchDAO.getRecentSearches()
    }

    fun insertSearch(searchData: SearchData){
        searchDAO.insertSearch(searchData)
    }
}