package com.exraion.beu.data.source.local.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exraion.beu.data.source.local.database.entity.UserEntity

@Database(entities = [UserEntity::class], version = 2)
abstract class BeUDatabase: RoomDatabase() {
    abstract fun beuDao(): BeUDao
}