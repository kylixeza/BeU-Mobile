package com.exraion.beu.data.source.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey
    @ColumnInfo("token")
    val token: String,
    
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "avatar")
    val avatar: String,

    @ColumnInfo(name = "beu_pay")
    val beuPay: Int,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,

    @ColumnInfo(name = "xp")
    val xp: Int
)
