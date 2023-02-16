package com.exraion.beu.data.source.remote

import com.exraion.beu.base.BaseRemoteResponse
import com.exraion.beu.data.source.remote.api.model.BaseResponse
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.auth.TokenResponse
import com.exraion.beu.data.source.remote.api.service.ApiService

class RemoteDataSource(
    private val apiService: ApiService
) {
    suspend fun signUp(
        body: RegisterBody
    ) = object : BaseRemoteResponse<TokenResponse>() {
        override suspend fun call(): BaseResponse<TokenResponse> = apiService.signUp(body)
    }

    suspend fun signIn(
        body: LoginBody
    ) = object : BaseRemoteResponse<TokenResponse>() {
        override suspend fun call(): BaseResponse<TokenResponse> = apiService.signIn(body)
    }

    suspend fun signOut(
        token: String
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.signOut(token)
    }
}