package com.exraion.beu.data.source.remote

import com.exraion.beu.base.BaseRemoteResponse
import com.exraion.beu.data.source.remote.api.model.BaseResponse
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.auth.TokenResponse
import com.exraion.beu.data.source.remote.api.model.favorite.FavoriteBody
import com.exraion.beu.data.source.remote.api.model.ingredient.IngredientResponse
import com.exraion.beu.data.source.remote.api.model.leaderboard.LeaderboardResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuDetailResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuListResponse
import com.exraion.beu.data.source.remote.api.model.user.UserResponse
import com.exraion.beu.data.source.remote.api.service.ApiService

class RemoteDataSource(
    private val apiService: ApiService
) {
    suspend fun signUp(
        body: RegisterBody
    ) = object : BaseRemoteResponse<TokenResponse>() {
        override suspend fun call(): BaseResponse<TokenResponse> = apiService.signUp(body)
    }.asFlow()

    suspend fun signIn(
        body: LoginBody
    ) = object : BaseRemoteResponse<TokenResponse>() {
        override suspend fun call(): BaseResponse<TokenResponse> = apiService.signIn(body)
    }.asFlow()

    suspend fun signOut(
        token: String
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.signOut(token)
    }.asFlow()
    
    suspend fun fetchUserDetail(
        token: String
    ) = object : BaseRemoteResponse<UserResponse>() {
        override suspend fun call(): BaseResponse<UserResponse> = apiService.fetchUserDetail(token)
    }.asFlow()
    
    suspend fun postFavorite(
        token: String,
        body: FavoriteBody
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.postFavorite(token, body)
    }.asFlow()
    
    suspend fun deleteFavorite(
        token: String,
        menuId: String
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.deleteFavorite(token, menuId)
    }.asFlow()
    
    suspend fun fetchMenus(
        token: String
    ) = object : BaseRemoteResponse<List<MenuListResponse>>() {
        override suspend fun call(): BaseResponse<List<MenuListResponse>> = apiService.fetchMenus(token)
    }.asFlow()
    
    suspend fun fetchSearchedMenus(
        token: String,
        query: String
    ) = object : BaseRemoteResponse<List<MenuListResponse>>() {
        override suspend fun call(): BaseResponse<List<MenuListResponse>> = apiService.fetchSearchedMenus(token, query)
    }.asFlow()
    
    suspend fun fetchCategorizedMenus(
        token: String,
        category: String
    ) = object : BaseRemoteResponse<List<MenuListResponse>>() {
        override suspend fun call(): BaseResponse<List<MenuListResponse>> = apiService.fetchCategorizedMenus(token, category)
    }.asFlow()
    
    suspend fun fetchDietMenus(
        token: String
    ) = object : BaseRemoteResponse<List<MenuListResponse>>() {
        override suspend fun call(): BaseResponse<List<MenuListResponse>> = apiService.fetchDietMenus(token)
    }.asFlow()
    
    suspend fun fetchMenuDetail(
        token: String,
        menuId: String
    ) = object : BaseRemoteResponse<MenuDetailResponse>() {
        override suspend fun call(): BaseResponse<MenuDetailResponse> = apiService.fetchMenuDetail(token, menuId)
    }.asFlow()
    
    suspend fun fetchMenuIngredients(
        token: String,
        menuId: String
    ) = object : BaseRemoteResponse<List<IngredientResponse>>() {
        override suspend fun call(): BaseResponse<List<IngredientResponse>> = apiService.fetchMenuIngredients(token, menuId)
    }.asFlow()
    
    suspend fun fetchLeaderboard(
        token: String
    ) = object : BaseRemoteResponse<List<LeaderboardResponse>>() {
        override suspend fun call(): BaseResponse<List<LeaderboardResponse>> = apiService.fetchLeaderboard(token)
    }.asFlow()
    
    suspend fun fetchMyRank(
        token: String
    ) = object : BaseRemoteResponse<LeaderboardResponse>() {
        override suspend fun call(): BaseResponse<LeaderboardResponse> = apiService.fetchMyRank(token)
    }.asFlow()
}