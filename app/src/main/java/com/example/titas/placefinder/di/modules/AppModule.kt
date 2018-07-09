package com.example.titas.placefinder.di.modules

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import com.example.titas.placefinder.repository.dao.SearchDAO
import com.example.titas.placefinder.repository.dao.SearchDatabase
import com.example.titas.placefinder.viewmodel.SearchViewModelFactory
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
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

//    @Provides
//    @Singleton
//    fun provideSearchViewModelFactory(factory: SearchViewModelFactory): ViewModelProvider.Factory = factory
}