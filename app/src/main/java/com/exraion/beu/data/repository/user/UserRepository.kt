package com.exraion.beu.data.repository.user

import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun signUp(body: RegisterBody): Flow<Resource<Unit>>
    fun signIn(body: LoginBody): Flow<Resource<Unit>>
    fun signOut(): Flow<Resource<Unit>>
}