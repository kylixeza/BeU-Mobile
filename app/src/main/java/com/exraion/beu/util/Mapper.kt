package com.exraion.beu.util

import com.exraion.beu.data.source.local.database.entity.UserEntity
import com.exraion.beu.data.source.remote.api.model.ingredient.IngredientResponse
import com.exraion.beu.data.source.remote.api.model.leaderboard.LeaderboardResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuDetailResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuListResponse
import com.exraion.beu.data.source.remote.api.model.review.ReviewResponse
import com.exraion.beu.data.source.remote.api.model.user.UserResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherAvailableResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherDetailResponse
import com.exraion.beu.data.source.remote.api.model.voucher.VoucherListResponse
import com.exraion.beu.model.*

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
    this.isFavorite
)

fun MenuDetailResponse.toMenuDetail() = MenuDetail(
    menuId,
    title,
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