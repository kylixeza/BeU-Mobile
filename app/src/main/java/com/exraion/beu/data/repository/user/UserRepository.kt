package com.exraion.beu.data.repository.user

import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.favorite.FavoriteBody
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun signUp(body: RegisterBody): Flow<Resource<Unit>>
    fun signIn(body: LoginBody): Flow<Resource<Unit>>
    fun signOut(): Flow<Resource<Unit>>
    fun fetchUserDetail(): Flow<Resource<Unit>>
    fun getUserDetail(): Flow<Resource<User>>
    fun postFavorite(body: FavoriteBody): Flow<Resource<Unit>>
    fun deleteFavorite(menuId: String): Flow<Resource<Unit>>

    suspend fun savePrefIsLogin(isLogin: Boolean)
    suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean)
    suspend fun savePrefToken(token: String)
    fun readPrefIsLogin(): Flow<Boolean>
    fun readPrefHaveRunAppBefore(): Flow<Boolean>
    fun readPrefToken(): Flow<String?>
}