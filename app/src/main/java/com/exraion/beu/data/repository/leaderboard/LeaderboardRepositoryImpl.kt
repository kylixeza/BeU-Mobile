package com.exraion.beu.data.repository.leaderboard

import com.exraion.beu.base.NetworkOnlyResource
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.leaderboard.LeaderboardResponse
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.Leaderboard
import com.exraion.beu.util.toLeaderboard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class LeaderboardRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): LeaderboardRepository {
    override fun fetchLeaderboard(): Flow<Resource<List<Leaderboard>>> =
        object : NetworkOnlyResource<List<Leaderboard>, List<LeaderboardResponse>?>() {
            override suspend fun createCall(): Flow<RemoteResponse<List<LeaderboardResponse>?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchLeaderboard(token)
            }
    
            override fun mapTransform(data: List<LeaderboardResponse>?): List<Leaderboard> {
                return data?.map { it.toLeaderboard() } ?: emptyList()
            }
    
        }.asFlow()
    
    override fun fetchMyRank(): Flow<Resource<Leaderboard>> =
        object : NetworkOnlyResource<Leaderboard, LeaderboardResponse?>() {
            override suspend fun createCall(): Flow<RemoteResponse<LeaderboardResponse?>> {
                val token = localDataSource.readPrefToken().first() ?: ""
                return remoteDataSource.fetchMyRank(token)
            }
    
            override fun mapTransform(data: LeaderboardResponse?): Leaderboard {
                return data!!.toLeaderboard()
            }
    
        }.asFlow()
}