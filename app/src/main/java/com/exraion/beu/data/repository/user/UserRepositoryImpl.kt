package com.exraion.beu.data.repository.user

import com.exraion.beu.base.NetworkBoundRequest
import com.exraion.beu.base.NetworkOnlyResource
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.auth.TokenResponse
import com.exraion.beu.data.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

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