package com.exraion.beu.data.source.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreUtil {
    const val DATA_STORE_NAME = "BEU_DATA_STORE"
    val IS_LOGIN_PREF_KEY = booleanPreferencesKey("isLogin")
    val HAVE_RUN_APP_BEFORE_PREF_KEY = booleanPreferencesKey("isFirstTime")
    val TOKEN_PREF_KEY = stringPreferencesKey("token")
    
    val WEIGHT_PREF_KEY = intPreferencesKey("weight")
    val HEIGHT_PREF_KEY = intPreferencesKey("height")
    val AGE_PREF_KEY = intPreferencesKey("age")
    val CALORIES_NEEDED_PREF_KEY = intPreferencesKey("caloriesNeeded")
}