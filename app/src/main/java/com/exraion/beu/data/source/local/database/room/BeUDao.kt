package com.exraion.beu.data.source.local.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.exraion.beu.data.source.local.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BeUDao {
    
    @Insert
    suspend fun insertUser(user: UserEntity)
    
    @Query("SELECT * FROM user_table WHERE token = :token")
    fun getUser(token: String): Flow<UserEntity>
    
}