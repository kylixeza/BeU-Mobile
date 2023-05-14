package com.exraion.beu.data.repository.daily_xp

import com.exraion.beu.data.source.remote.api.model.dailyxp.DailyXpRequest
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.DailyXp
import kotlinx.coroutines.flow.Flow

interface DailyXpRepository {
    suspend fun checkDailyXp(): Flow<Resource<Unit>>
    suspend fun fetchDailyXps(): Flow<Resource<List<DailyXp>>>
    suspend fun fetchTodayDailyXp(): Flow<Resource<DailyXp>>
    suspend fun takeDailyXp(body: DailyXpRequest): Flow<Resource<Unit>>
}