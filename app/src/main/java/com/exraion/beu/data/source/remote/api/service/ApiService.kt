package com.exraion.beu.data.source.remote.api.service

import com.exraion.beu.data.source.remote.api.model.BaseResponse
import com.exraion.beu.data.source.remote.api.model.auth.LoginBody
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.auth.TokenResponse
import com.exraion.beu.data.source.remote.api.model.dailyxp.DailyXpRequest
import com.exraion.beu.data.source.remote.api.model.dailyxp.DailyXpResponse
import com.exraion.beu.data.source.remote.api.model.favorite.FavoriteBody
import com.exraion.beu.data.source.remote.api.model.history.HistoryResponse
import com.exraion.beu.data.source.remote.api.model.history.HistoryUpdateStarsGiven
import com.exraion.beu.data.source.remote.api.model.ingredient.IngredientResponse
import com.exraion.beu.data.source.remote.api.model.leaderboard.LeaderboardResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuDetailResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuListResponse
import com.exraion.beu.data.source.remote.api.model.order.OrderBody
import com.exraion.beu.data.source.remote.api.model.user.UserResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherAvailableResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherDetailResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherListResponse
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
    ): BaseResponse<List<IngredientResponse>>
    
    @GET("leaderboard")
    suspend fun fetchLeaderboard(
        @Header("Authorization") token: String
    ): BaseResponse<List<LeaderboardResponse>>
    
    @GET("leaderboard/me")
    suspend fun fetchMyRank(
        @Header("Authorization") token: String
    ): BaseResponse<LeaderboardResponse>
    
    @GET("voucher/available")
    suspend fun fetchAvailableVouchers(
        @Header("Authorization") token: String
    ): BaseResponse<VoucherAvailableResponse>

    @PUT("voucher/{voucherId}/redeem")
    suspend fun redeemVoucher(
        @Header("Authorization") token: String,
        @Path("voucherId") voucherId: String
    ): BaseResponse<String>

    @GET("voucher/user")
    suspend fun fetchUserVouchers(
        @Header("Authorization") token: String
    ): BaseResponse<List<VoucherListResponse>>

    @GET("voucher/{voucherId}")
    suspend fun fetchVoucherDetail(
        @Header("Authorization") token: String,
        @Path("voucherId") voucherId: String
    ): BaseResponse<VoucherDetailResponse>

    @PUT("voucher/{voucherId}/use")
    suspend fun useVoucher(
        @Header("Authorization") token: String,
        @Path("voucherId") voucherId: String
    ): BaseResponse<String>

    @PUT("voucher/secret/{secretKey}/redeem")
    suspend fun redeemVoucherBySecretKey(
        @Header("Authorization") token: String,
        @Path("secretKey") secretKey: String
    ): BaseResponse<String>

    @POST("user/order")
    suspend fun postOrder(
        @Header("Authorization") token: String,
        @Body body: OrderBody
    ): BaseResponse<String>

    @GET("user/order")
    suspend fun fetchUserOrders(
        @Header("Authorization") token: String
    ): BaseResponse<List<HistoryResponse>>

    @PUT("user/order/{orderId}/cancel")
    suspend fun cancelOrder(
        @Header("Authorization") token: String,
        @Path("orderId") orderId: String
    ): BaseResponse<String>

    @PUT("user/order/{orderId}/rating")
    suspend fun rateOrder(
        @Header("Authorization") token: String,
        @Path("orderId") orderId: String,
        @Body body: HistoryUpdateStarsGiven
    ): BaseResponse<String>

    @GET("dailyxp/check")
    suspend fun checkDailyXp(
        @Header("Authorization") token: String
    ): BaseResponse<String>

    @GET("dailyxp")
    suspend fun fetchDailyXps(
        @Header("Authorization") token: String
    ): BaseResponse<List<DailyXpResponse>>

    @GET("dailyxp/today")
    suspend fun fetchTodayDailyXp(
        @Header("Authorization") token: String
    ): BaseResponse<DailyXpResponse>

    @POST("dailyxp/take")
    suspend fun takeDailyXp(
        @Header("Authorization") token: String,
        @Body body: DailyXpRequest
    ): BaseResponse<String>

}