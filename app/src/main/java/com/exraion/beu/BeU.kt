package com.exraion.beu

import android.app.Application
import com.exraion.beu.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BeU: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BeU)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    dataStoreModule,
                    dataSourceModule,
                    repositoryModule,
                    viewModelModule,
                    adapterModule
                )
            )
        }
    }
}