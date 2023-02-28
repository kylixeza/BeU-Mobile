package com.exraion.beu.data.source.remote.api.service

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
import retrofit2.http.*

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

    @GET("user")
    suspend fun fetchUserDetail(
        @Header("Authorization") token: String
    ): BaseResponse<UserResponse>
    
    @POST("user/favorite")
    suspend fun postFavorite(
        @Header("Authorization") token: String,
        @Body body: FavoriteBody
    ): BaseResponse<String>
    
    @DELETE("user/favorite/{menuId}")
    suspend fun deleteFavorite(
        @Header("Authorization") token: String,
        @Path("menuId") menuId: String
    ): BaseResponse<String>
    
    @GET("menu")
    suspend fun fetchMenus(
        @Header("Authorization") token: String
    ): BaseResponse<List<MenuListResponse>>
    
    @GET("menu")
    suspend fun fetchSearchedMenus(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): BaseResponse<List<MenuListResponse>>
    
    @GET("menu")
    suspend fun fetchCategorizedMenus(
        @Header("Authorization") token: String,
        @Query("category") category: String
    ): BaseResponse<List<MenuListResponse>>
    
    @GET("menu/diet")
    suspend fun fetchDietMenus(
        @Header("Authorization") token: String
    ): BaseResponse<List<MenuListResponse>>
    
    @GET("menu/{menuId}")
    suspend fun fetchMenuDetail(
        @Header("Authorization") token: String,
        @Path("menuId") menuId: String
    ): BaseResponse<MenuDetailResponse>
    
    @GET("menu/{menuId}/ingredients")
    suspend fun fetchMenuIngredients(
        @Header("Authorization") token: String,
        @Path("menuId") menuId: String
    ): BaseResponse<IngredientResponse>
    
    @GET("leaderboard")
    suspend fun fetchLeaderboard(
        @Header("Authorization") token: String
    ): BaseResponse<List<LeaderboardResponse>>
    
    @GET("leaderboard/me")
    suspend fun fetchMyRank(
        @Header("Authorization") token: String
    ): BaseResponse<LeaderboardResponse>
}