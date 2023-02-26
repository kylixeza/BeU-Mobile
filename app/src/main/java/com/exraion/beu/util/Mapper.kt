package com.exraion.beu.util

import com.exraion.beu.data.source.local.database.entity.UserEntity
import com.exraion.beu.data.source.remote.api.model.user.UserResponse
import com.exraion.beu.model.User

fun UserResponse.toUser() = User(
    this.location,
    this.avatar,
    this.beUPay,
    this.email,
    this.name,
    this.phoneNumber,
    this.xp
)

fun UserResponse.toUserEntity(
    token: String
) = UserEntity(
    token,
    this.name,
    this.location,
    this.avatar,
    this.beUPay,
    this.email,
    this.phoneNumber,
    this.xp
)

fun UserEntity.toUser() = User(
    this.location,
    this.avatar,
    this.beuPay,
    this.email,
    this.name,
    this.phoneNumber,
    this.xp
)