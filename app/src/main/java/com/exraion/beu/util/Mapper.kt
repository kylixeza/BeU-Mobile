package com.exraion.beu.util

import com.exraion.beu.data.source.local.database.entity.UserEntity
import com.exraion.beu.data.source.remote.api.model.ingredient.IngredientResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuDetailResponse
import com.exraion.beu.data.source.remote.api.model.menu.MenuListResponse
import com.exraion.beu.data.source.remote.api.model.user.UserResponse
import com.exraion.beu.model.Ingredient
import com.exraion.beu.model.MenuDetail
import com.exraion.beu.model.MenuList
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

fun MenuListResponse.toMenuList() = MenuList(
    this.menuId,
    this.image,
    this.title,
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
    reviews
)

fun IngredientResponse.toIngredient() = Ingredient(
    menuId,
    ingredients
)