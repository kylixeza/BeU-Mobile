package com.exraion.beu.data.source.local

import com.exraion.beu.base.BaseDatabaseAnswer
import com.exraion.beu.data.source.local.database.entity.UserEntity
import com.exraion.beu.data.source.local.database.room.BeUDao
import com.exraion.beu.data.source.local.datastore.BeUDataStore
import kotlinx.coroutines.flow.first

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
    
    suspend fun insertUser(user: UserEntity) {
        dao.insertUser(user)
    }
    
    fun getUserDetail(token: String) = object: BaseDatabaseAnswer<UserEntity>() {
        override suspend fun callDatabase(): UserEntity {
            return dao.getUser(token).first()
        }
    }.doObservable()

    suspend fun deleteUser(token: String) {
        dao.deleteUser(token)
    }

    fun getUserXp(token: String) = dao.getUserXp(token)

    suspend fun increaseXp(token: String, givenXp: Int) {
        val currentXp = dao.getUserXp(token).first()
        dao.updateXp(token, currentXp + givenXp)
    }

    suspend fun decreaseXp(token: String, costXp: Int) {
        val currentXp = dao.getUserXp(token).first()
        dao.updateXp(token, currentXp - costXp)
    }

    fun readPrefIsLogin() = dataStore.readPrefIsLogin()

    fun readPrefHaveRunAppBefore() = dataStore.readPrefHaveRunAppBefore()

    fun readPrefToken() = dataStore.readPrefToken()

}