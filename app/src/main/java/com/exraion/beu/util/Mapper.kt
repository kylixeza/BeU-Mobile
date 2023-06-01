package com.exraion.beu.util

import com.exraion.beu.data.source.local.database.entity.UserEntity
import com.exraion.beu.data.source.remote.api.model.dailyxp.DailyXpResponse
import com.exraion.beu.data.source.remote.api.model.history.HistoryResponse
import com.exraion.beu.data.source.remote.api.model.ingredient.IngredientResponse
import com.exraion.beu.data.source.remote.api.model.leaderboard.LeaderboardResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuDetailResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuListResponse
import com.exraion.beu.data.source.remote.api.model.review.ReviewResponse
import com.exraion.beu.data.source.remote.api.model.user.UserResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherAvailableResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherDetailResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherListResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherSecretResponse
import com.exraion.beu.model.DailyXp
import com.exraion.beu.model.History
import com.exraion.beu.model.Ingredient
import com.exraion.beu.model.Leaderboard
import com.exraion.beu.model.MenuDetail
import com.exraion.beu.model.MenuList
import com.exraion.beu.model.Review
import com.exraion.beu.model.User
import com.exraion.beu.model.VoucherAvailable
import com.exraion.beu.model.VoucherDetail
import com.exraion.beu.model.VoucherList
import com.exraion.beu.model.VoucherSecret

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

fun MenuListResponse.toMenuList() = MenuList(
    this.menuId,
    this.image,
    this.title,
    this.difficulty,
    this.rangePrice,
    this.rating,
    this.cookTime,
    this.isFavorite,
    this.isExclusive
)

fun MenuDetailResponse.toMenuDetail() = MenuDetail(
    menuId,
    title,
    image,
    videoUrl,
    isFavorite,
    isAvailable,
    ingredients,
    tools,
    steps,
    description,
    estimatedTime,
    benefit,
    reviewsCount,
    averageRating,
    reviews.map { it.toReview() }
)

fun ReviewResponse.toReview() = Review(
    name,
    avatar,
    rating
)

fun IngredientResponse.toIngredient() = Ingredient(
    ingredient, price
)

fun LeaderboardResponse.toLeaderboard() = Leaderboard(
    name, avatar, xp, rank
)

fun VoucherAvailableResponse.toVoucherAvailable() = VoucherAvailable(
    shipping = shipping.map { it.toVoucherList() },
    product = product.map { it.toVoucherList() },
)

fun VoucherListResponse.toVoucherList() = VoucherList(
    voucherId, category, xpCost, validUntil, discount, minimumSpend, maximumDiscount
)

fun VoucherDetailResponse.toVoucherDetail() = VoucherDetail(
    voucherId, category, xpCost, validUntil, discount, minimumSpend, maximumDiscount
)

fun VoucherList.toVoucherDetail() = VoucherDetail(
    voucherId, category, xpCost, validUntil, discount, minimumSpend, maximumDiscount
)

fun VoucherSecretResponse.toVoucherSecret() = VoucherSecret(
    isSuccessRedeemed, message
)

fun HistoryResponse.toHistory() = History(
    orderId,
    menuId,
    title,
    image,
    timeStamp,
    ingredients,
    status,
    starsGiven
)

fun DailyXpResponse.toDailyXp() = DailyXp(
    dailyXpId,
    dailyXp,
    day,
    isTaken
)