package com.exraion.beu.data.source.remote.api.service

import com.exraion.beu.data.source.remote.api.model.BaseResponse
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("signup")
    suspend fun signUp(
        @Body body: RegisterBody
    ): BaseResponse<TokenResponse>

    @POST("signin")
    suspend fun signIn(
        @Body body: LoginBody
    ): BaseResponse<TokenResponse>

    @POST("signout")
    suspend fun signOut(
        @Header("Authorization") token: String
    ): BaseResponse<String>

}