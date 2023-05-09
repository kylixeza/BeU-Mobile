package com.exraion.beu.data.source.remote.api.model.menu

import com.exraion.beu.data.source.remote.api.model.review.ReviewResponse
import com.google.gson.annotations.SerializedName

data class MenuDetailResponse(
    @field:SerializedName("menu_id")
    val menuId: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("video_url")
    val videoUrl: String,

    @field:SerializedName("is_favorite")
    val isFavorite: Boolean,

    @field:SerializedName("is_available")
    val isAvailable: Boolean,

    @field:SerializedName("ingredients")
    val ingredients: List<String>,

    @field:SerializedName("tools")
    val tools: List<String>,

    @field:SerializedName("steps")
    val steps: List<String>,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("estimated_time")
    val estimatedTime: String,

    @field:SerializedName("benefit")
    val benefit: String,

    @field:SerializedName("reviews_count")
    val reviewsCount: Int,

    @field:SerializedName("average_rating")
    val averageRating: Double,

    @field:SerializedName("reviews")
    val reviews: List<ReviewResponse>
)
