package com.example.titas.placefinder.di.component

import android.app.Application
import com.example.titas.placefinder.di.modules.AppModule
import com.example.titas.placefinder.di.modules.BuildersModule
import com.example.titas.placefinder.di.modules.NetModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Titas on 7/6/2018.
 */

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, BuildersModule::class, AppModule::class, NetModule::class))
interface AppComponent {
    fun inject(app: Application)
}