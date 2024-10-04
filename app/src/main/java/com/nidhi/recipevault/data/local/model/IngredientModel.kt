package com.nidhi.recipevault.data.local.model


import com.google.gson.annotations.SerializedName

data class IngredientModel(
    @SerializedName("item")
    val item: String,
    @SerializedName("maxQty")
    val maxQty: Double,
    @SerializedName("qty")
    val qty: Double,
    @SerializedName("unit")
    val unit: String
)