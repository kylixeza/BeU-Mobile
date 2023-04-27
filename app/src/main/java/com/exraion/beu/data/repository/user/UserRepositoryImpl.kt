package com.exraion.beu.data.repository.user

import com.exraion.beu.base.DatabaseOnlyResource
import com.exraion.beu.base.NetworkBoundRequest
import com.exraion.beu.base.NetworkOnlyResource
import com.exraion.beu.data.source.local.LocalAnswer
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.local.database.entity.UserEntity
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.auth.TokenResponse
import com.exraion.beu.data.source.remote.api.model.favorite.FavoriteBody
import com.exraion.beu.data.source.remote.api.model.user.UserResponse
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.User
import com.exraion.beu.util.toUser
import com.exraion.beu.util.toUserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): UserRepository {

    override fun signUp(body: RegisterBody): Flow<Resource<Unit>> = object: NetworkBoundRequest<TokenResponse?>() {
        override suspend fun createCall(): Flow<RemoteResponse<TokenResponse?>> {
            return remoteDataSource.signUp(body)
        }

        override suspend fun saveCallResult(data: TokenResponse?) {
            if(data != null) {
                localDataSource.savePrefToken(data.token)
            }
        }
    }.asFlow()

    override fun signIn(body: LoginBody): Flow<Resource<Unit>> = object : NetworkBoundRequest<TokenResponse?>() {
        override suspend fun createCall(): Flow<RemoteResponse<TokenResponse?>> {
            return remoteDataSource.signIn(body)
        }

        override suspend fun saveCallResult(data: TokenResponse?) {
            if(data != null) {
                localDataSource.savePrefToken(data.token)
            }
        }
    }.asFlow()

    override fun signOut(): Flow<Resource<Unit>> = object : NetworkOnlyResource<Unit, String?>() {
        override suspend fun createCall(): Flow<RemoteResponse<String?>> {
            val token = localDataSource.readPrefToken().firstOrNull() ?: ""
            return remoteDataSource.signOut(token)
        }

        override fun mapTransform(data: String?) {
            return
        }
    }.asFlow()
    
    override fun fetchUserDetail(): Flow<Resource<Unit>> = object : NetworkBoundRequest<UserResponse?>() {
        override suspend fun createCall(): Flow<RemoteResponse<UserResponse?>> {
            val token = localDataSource.readPrefToken().firstOrNull() ?: ""
            return remoteDataSource.fetchUserDetail(token)
        }

        override suspend fun saveCallResult(data: UserResponse?) {
            val token = localDataSource.readPrefToken().firstOrNull() ?: ""
            if (data != null) {
                localDataSource.insertUser(data.toUserEntity(token))
            }
        }
    }.asFlow()
    
    override fun getUserDetail(): Flow<Resource<User>> = object : DatabaseOnlyResource<User, UserEntity>() {
        override suspend fun loadFromDb(): Flow<LocalAnswer<UserEntity>> {
            val token = localDataSource.readPrefToken().firstOrNull() ?: ""
            return localDataSource.getUserDetail(token)
        }
    
        override suspend fun mapTransform(data: UserEntity): User {
            return data.toUser()
        }
    
    }.asFlow()

    override fun getUserXp(): Flow<Int> = flow {
        val token = localDataSource.readPrefToken().firstOrNull().orEmpty()
        emit(localDataSource.getUserXp(token).first())
    }

    override suspend fun increaseUserXp(givenXp: Int) {
        val token = localDataSource.readPrefToken().firstOrNull().orEmpty()
        localDataSource.increaseXp(token, givenXp)
    }

    override suspend fun decreaseUserXp(costXp: Int) {
        val token = localDataSource.readPrefToken().firstOrNull().orEmpty()
        localDataSource.decreaseXp(token, costXp)
    }

    override fun postFavorite(body: FavoriteBody): Flow<Resource<Unit>> = object : NetworkOnlyResource<Unit, String?>() {
        override suspend fun createCall(): Flow<RemoteResponse<String?>> {
            val token = localDataSource.readPrefToken().firstOrNull() ?: ""
            return remoteDataSource.postFavorite(token, body)
        }
    
        override fun mapTransform(data: String?) {
            return
        }
    }.asFlow()
    
    override fun deleteFavorite(menuId: String): Flow<Resource<Unit>> = object : NetworkOnlyResource<Unit, String?>() {
        override suspend fun createCall(): Flow<RemoteResponse<String?>> {
            val token = localDataSource.readPrefToken().firstOrNull() ?: ""
            return remoteDataSource.deleteFavorite(token, menuId)
        }
    
        override fun mapTransform(data: String?) {
            return
        }
    }.asFlow()
    
    override suspend fun savePrefIsLogin(isLogin: Boolean) {
        localDataSource.savePrefIsLogin(isLogin)
    }

    override suspend fun savePrefHaveRunAppBefore(isFirstTime: Boolean) {
        localDataSource.savePrefHaveRunAppBefore(isFirstTime)
    }

    override suspend fun savePrefToken(token: String) {
        localDataSource.savePrefToken(token)
    }

    override fun readPrefToken(): Flow<String?> = localDataSource.readPrefToken()

    override fun readPrefIsLogin(): Flow<Boolean> = localDataSource.readPrefIsLogin()

    override fun readPrefHaveRunAppBefore(): Flow<Boolean> = localDataSource.readPrefHaveRunAppBefore()
}