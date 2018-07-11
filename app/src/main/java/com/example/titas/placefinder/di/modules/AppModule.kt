package com.example.titas.placefinder.di.modules

import android.app.Application
import android.arch.persistence.room.Room
import com.example.titas.placefinder.repository.dao.FavoritePlacesDatabase
import com.example.titas.placefinder.repository.dao.FavoritesDAO
import com.example.titas.placefinder.repository.dao.SearchDAO
import com.example.titas.placefinder.repository.dao.SearchDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Titas on 7/6/2018.
 */

@Module
class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app

    @Provides
    @Singleton
    fun provideSearchDatabase(app: Application): SearchDatabase = Room.databaseBuilder(app, SearchDatabase::class.java,
            "search_db").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideSearchDAO(database: SearchDatabase): SearchDAO = database.getSearchDAO()

    @Provides
    @Singleton
    fun provideFavoritesDatabase(app: Application): FavoritePlacesDatabase = Room.databaseBuilder(app,
            FavoritePlacesDatabase::class.java, "favorites_db").fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideFavoritesDAO(database: FavoritePlacesDatabase): FavoritesDAO = database.getFavoritesDao()

//    @Provides
//    @Singleton
//    fun provideSearchViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory = factory
}