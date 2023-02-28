package com.exraion.beu.data.repository.leaderboard

import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.Leaderboard
import kotlinx.coroutines.flow.Flow

interface LeaderboardRepository {
    
    fun fetchLeaderboard(): Flow<Resource<List<Leaderboard>>>
    fun fetchMyRank(): Flow<Resource<Leaderboard>>
    
}