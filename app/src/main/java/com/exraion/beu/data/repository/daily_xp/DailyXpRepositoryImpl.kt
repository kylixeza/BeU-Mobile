package com.exraion.beu.data.repository.daily_xp

import com.exraion.beu.base.NetworkOnlyResource
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.dailyxp.DailyXpRequest
import com.exraion.beu.data.source.remote.api.model.dailyxp.DailyXpResponse
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.DailyXp
import com.exraion.beu.util.toDailyXp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class DailyXpRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): DailyXpRepository {
    override suspend fun checkDailyXp(): Flow<Resource<Unit>> =
        object : NetworkOnlyResource<Unit, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.checkDailyXp(token)
            }

            override fun mapTransform(data: String?) {
                return
            }

        }.asFlow()

    override suspend fun fetchDailyXps(): Flow<Resource<List<DailyXp>>> =
        object : NetworkOnlyResource<List<DailyXp>, List<DailyXpResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<DailyXpResponse>?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.fetchDailyXps(token)
            }

            override fun mapTransform(data: List<DailyXpResponse>?): List<DailyXp> {
                return data?.map { it.toDailyXp() }.orEmpty()
            }

        }.asFlow()

    override suspend fun fetchTodayDailyXp(): Flow<Resource<DailyXp>> =
        object : NetworkOnlyResource<DailyXp, DailyXpResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<DailyXpResponse?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.fetchTodayDailyXp(token)
            }

            override fun mapTransform(data: DailyXpResponse?): DailyXp {
                return data?.toDailyXp()!!
            }

        }.asFlow()
    override suspend fun takeDailyXp(body: DailyXpRequest): Flow<Resource<Unit>> =
        object : NetworkOnlyResource<Unit, String?>() {
            override suspend fun createCall(): Flow<RemoteResponse<String?>> {
                val token = localDataSource.readPrefToken().first().orEmpty()
                return remoteDataSource.takeDailyXp(token, body)
            }

            override fun mapTransform(data: String?) {
                return
            }

        }.asFlow()
}