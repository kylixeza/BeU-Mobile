package com.exraion.beu.data.source.remote

import com.exraion.beu.base.BaseRemoteResponse
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
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherSecretResponse
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

    suspend fun fetchExclusiveMenus(
        token: String
    ) = object : BaseRemoteResponse<List<MenuListResponse>>() {
        override suspend fun call(): BaseResponse<List<MenuListResponse>> = apiService.fetchExclusiveMenus(token)
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

    suspend fun fetchAvailableVouchers(
        token: String
    ) = object : BaseRemoteResponse<VoucherAvailableResponse>() {
        override suspend fun call(): BaseResponse<VoucherAvailableResponse> {
            return apiService.fetchAvailableVouchers(token)
        }
    }.asFlow()

    suspend fun redeemVoucher(
        token: String,
        voucherId: String
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.redeemVoucher(token, voucherId)
    }.asFlow()

    suspend fun fetchUserVouchers(
        token: String
    ) = object : BaseRemoteResponse<List<VoucherListResponse>>() {
        override suspend fun call(): BaseResponse<List<VoucherListResponse>> = apiService.fetchUserVouchers(token)
    }.asFlow()

    suspend fun fetchVoucherDetail(
        token: String,
        voucherId: String
    ) = object : BaseRemoteResponse<VoucherDetailResponse>() {
        override suspend fun call(): BaseResponse<VoucherDetailResponse> = apiService.fetchVoucherDetail(token, voucherId)
    }.asFlow()

    suspend fun useVoucher(
        token: String,
        voucherId: String
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.useVoucher(token, voucherId)
    }.asFlow()

    suspend fun redeemVoucherBySecretKey(
        token: String,
        secretKey: String
    ) = object : BaseRemoteResponse<VoucherSecretResponse>() {
        override suspend fun call(): BaseResponse<VoucherSecretResponse> = apiService.redeemVoucherBySecretKey(token, secretKey)
    }.asFlow()

    suspend fun postOrder(
        token: String,
        body: OrderBody
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.postOrder(token, body)
    }.asFlow()

    suspend fun fetchUserOrders(
        token: String
    ) = object : BaseRemoteResponse<List<HistoryResponse>>() {
        override suspend fun call(): BaseResponse<List<HistoryResponse>> = apiService.fetchUserOrders(token)
    }.asFlow()

    suspend fun cancelOrder(
        token: String,
        orderId: String
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.cancelOrder(token, orderId)
    }.asFlow()

    suspend fun rateOrder(
        token: String,
        orderId: String,
        body: HistoryUpdateStarsGiven
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.rateOrder(token, orderId, body)
    }.asFlow()

    suspend fun checkDailyXp(
        token: String
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.checkDailyXp(token)
    }.asFlow()

    suspend fun fetchDailyXps(
        token: String
    ) = object : BaseRemoteResponse<List<DailyXpResponse>>() {
        override suspend fun call(): BaseResponse<List<DailyXpResponse>> = apiService.fetchDailyXps(token)
    }.asFlow()

    suspend fun fetchTodayDailyXp(
        token: String
    ) = object : BaseRemoteResponse<DailyXpResponse>() {
        override suspend fun call(): BaseResponse<DailyXpResponse> = apiService.fetchTodayDailyXp(token)
    }.asFlow()

    suspend fun takeDailyXp(
        token: String,
        body: DailyXpRequest
    ) = object : BaseRemoteResponse<String>() {
        override suspend fun call(): BaseResponse<String> = apiService.takeDailyXp(token, body)
    }.asFlow()
}