package com.exraion.beu.data.source.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BeUDataStore(
    private val context: Context
) {
    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreUtil.DATA_STORE_NAME
    )

    suspend fun savePrefIsLogin(isLogin: Boolean) {
        context.userPreferenceDataStore.edit {
            it[DataStoreUtil.IS_LOGIN_PREF_KEY] = isLogin
        }
    }

    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) {
        context.userPreferenceDataStore.edit {
            it[DataStoreUtil.HAVE_RUN_APP_BEFORE_PREF_KEY] = isFirstTime
        }
    }

    suspend fun savePrefToken(token: String) {
        context.userPreferenceDataStore.edit {
            it[DataStoreUtil.TOKEN_PREF_KEY] = "Bearer $token"
        }
    }

    fun readPrefIsLogin(): Flow<Boolean> = context.userPreferenceDataStore.data
        .map {
            it[DataStoreUtil.IS_LOGIN_PREF_KEY] ?: false
        }

    fun readPrefHaveRunAppBefore(): Flow<Boolean> = context.userPreferenceDataStore.data
        .map {
            it[DataStoreUtil.HAVE_RUN_APP_BEFORE_PREF_KEY] ?: false
        }

    fun readPrefToken(): Flow<String?> = context.userPreferenceDataStore.data
        .map {
            it[DataStoreUtil.TOKEN_PREF_KEY]
        }

    suspend fun deletePrefToken() {
        context.userPreferenceDataStore.edit {
            it.remove(DataStoreUtil.TOKEN_PREF_KEY)
        }
    }
}