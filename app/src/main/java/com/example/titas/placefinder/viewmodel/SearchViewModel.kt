package com.example.titas.placefinder.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.os.AsyncTask
import com.example.titas.placefinder.repository.SearchRepository
import com.example.titas.placefinder.repository.model.SearchData
import javax.inject.Inject

/**
 * Created by Titas on 7/9/2018.
 */
class SearchViewModel @Inject constructor(private val repository: SearchRepository): ViewModel() {

    fun getSearchListObservable(): LiveData<List<SearchData>> {
        return repository.getRecentSearches()
    }

    fun insertSearch(searchData: SearchData) {
        AsyncTask.execute(Runnable {
            repository.insertSearch(searchData)
        })
    }
}