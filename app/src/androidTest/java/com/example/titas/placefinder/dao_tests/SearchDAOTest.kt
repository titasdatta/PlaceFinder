package com.example.titas.placefinder.dao_tests

import android.app.Instrumentation
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import com.example.titas.placefinder.repository.dao.SearchDAO
import com.example.titas.placefinder.repository.dao.SearchDatabase
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by Titas on 7/11/2018.
 */
@RunWith(JUnit4::class)
class SearchDAOTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var searchDatabase: SearchDatabase
    private lateinit var searchDao: SearchDAO

    @Before
    fun initDB(){
        searchDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), SearchDatabase::class.java).allowMainThreadQueries().build()
        searchDao = searchDatabase.getSearchDAO()
    }

    @After
    fun closeDB(){
        searchDatabase.close()
    }

    @Test
    fun insertSearchSavesData(){
        val mockSearchDataList = FakeSearchData.getFakeSearchDataList(3)
        mockSearchDataList.forEach {
            searchDao.insertSearch(it)
        }
        Assert.assertEquals(3, LiveDataTestUtil.getValue(searchDao.getRecentSearches()).size)
    }

    @Test
    fun getSingleSearchRecordReturnsCorrectSearch(){
        val mockSearchData = FakeSearchData.getFakeSearchData()
        searchDao.insertSearch(mockSearchData)
        val searchData = searchDao.getSingleSearchRecord(mockSearchData.searchTitle)
        Assert.assertEquals(mockSearchData.searchTitle, searchData.searchTitle)
    }

    fun updateSearchUpdatesCorrectly(){
        val mockSearchData = FakeSearchData.getFakeSearchData()
        searchDao.insertSearch(mockSearchData)
        searchDao.updateSearch(mockSearchData.searchTitle, 1001L)
        val searchData = searchDao.getSingleSearchRecord(mockSearchData.searchTitle)
        Assert.assertEquals(searchData.searchTime, 1001L)
    }
}