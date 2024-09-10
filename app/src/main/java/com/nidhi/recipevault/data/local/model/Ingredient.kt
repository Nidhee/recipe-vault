package com.nidhi.recipevault.data.local.model


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("item")
    val item: String,
    @SerializedName("maxQty")
    val maxQty: Int,
    @SerializedName("qty")
    val qty: Double,
    @SerializedName("unit")
    val unit: String
)