package com.exraion.beu.di

import androidx.room.Room
import com.exraion.beu.data.repository.menu.MenuRepository
import com.exraion.beu.data.repository.menu.MenuRepositoryImpl
import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.repository.user.UserRepositoryImpl
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.local.database.room.BeUDatabase
import com.exraion.beu.data.source.local.datastore.BeUDataStore
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.api.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<BeUDatabase>().beuDao()
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            BeUDatabase::class.java, "beu.db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}

val dataStoreModule = module {
    single {
        BeUDataStore(androidApplication())
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor {
                val logger = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                logger.intercept(it)
            }
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .cache(
                okhttp3.Cache(
                    androidContext().cacheDir,
                    10 * 1024 * 1024
                )
            )
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-exraion-beu.herokuapp.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val dataSourceModule = module {
    single { RemoteDataSource(get()) }
    single { LocalDataSource(get(), get()) }
}

val repositoryModule = module {
    single<UserRepository> {
        UserRepositoryImpl(get(), get())
    }
    
    single<MenuRepository> {
        MenuRepositoryImpl(get(), get())
    }
}