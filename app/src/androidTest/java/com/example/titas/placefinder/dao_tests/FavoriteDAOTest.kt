package com.example.titas.placefinder.dao_tests

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.example.titas.placefinder.repository.dao.FavoritePlacesDatabase
import com.example.titas.placefinder.repository.dao.FavoritesDAO
import com.example.titas.placefinder.repository.model.SavedPlace
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Titas on 7/12/2018.
 */
@RunWith(JUnit4::class)
class FavoriteDAOTest {
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var favoriteDB: FavoritePlacesDatabase
    private lateinit var favoriteDao: FavoritesDAO

    @Before
    fun initDB(){
        favoriteDB = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), FavoritePlacesDatabase::class.java)
                .allowMainThreadQueries().build()
        favoriteDao = favoriteDB.getFavoritesDao()
    }

    @After
    fun closeDB(){
        favoriteDB.close()
    }

    @Test
    fun insertFavoriteInsertsCorrectly(){
        val mockSavedPlace = SavedPlace(name = "Place", address = "address", isOpen = true, rating = 0.0, latitude = 0.0, longitude = 0.0)
        favoriteDao.insertPlace(savedPlace = mockSavedPlace)
        val resultSavedPlace = favoriteDao.getSavedPlaces()
        Assert.assertEquals("Place", LiveDataTestUtil.getValue(resultSavedPlace)[0].name)
    }

    fun deleteFavoriteDeletesCorrectly() {
        val mockSavedPlace = SavedPlace(name = "Place", address = "address", isOpen = true, rating = 0.0, latitude = 0.0, longitude = 0.0)
        favoriteDao.insertPlace(mockSavedPlace)
        favoriteDao.removePlace("Place")
        val resultSavedPlace = favoriteDao.getSavedPlaces()
        Assert.assertEquals(0, LiveDataTestUtil.getValue(resultSavedPlace).size)
    }


}