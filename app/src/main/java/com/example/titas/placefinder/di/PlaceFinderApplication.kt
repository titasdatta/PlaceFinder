package com.example.titas.placefinder.di

import android.app.Activity
import android.app.Application
import com.example.titas.placefinder.di.component.DaggerAppComponent
import com.example.titas.placefinder.di.modules.AppModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Titas on 7/6/2018.
 */
class PlaceFinderApplication: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}