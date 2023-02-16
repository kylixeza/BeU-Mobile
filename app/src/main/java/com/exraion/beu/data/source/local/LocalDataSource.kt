package com.exraion.beu.data.source.local

import com.exraion.beu.data.source.local.database.room.BeUDao
import com.exraion.beu.data.source.local.datastore.BeUDataStore
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val dao: BeUDao,
    private val dataStore: BeUDataStore
) {

    suspend fun savePrefIsLogin(isLogin: Boolean) {
        dataStore.savePrefIsLogin(isLogin)
    }

    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) {
        dataStore.savePrefHaveRunAppBefore(isFirstTime)
    }

    suspend fun savePrefToken(token: String) {
        dataStore.savePrefToken(token)
    }

    suspend fun deleteToken(token: String) {
        dataStore.deletePrefToken()
    }

    fun readPrefIsLogin() = dataStore.readPrefIsLogin()

    fun readPrefHaveRunAppBefore() = dataStore.readPrefHaveRunAppBefore()

    fun readPrefToken() = dataStore.readPrefToken()

}