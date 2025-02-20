package com.nidhi.recipevault.com.nidhi.recipevault.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Ingredient(
    val ingredientId: Int = 0,
    val recipeId: Int,
    val item: String,
    val qty: Double?,
    val maxQty: Double?,
    val unit: RecipeUnitDomain?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        ingredientId = parcel.readInt(),
        recipeId = parcel.readInt(),
        item = parcel.readString() ?: "",
        qty = parcel.readValue(Double::class.java.classLoader) as? Double,
        maxQty = parcel.readValue(Double::class.java.classLoader) as? Double,
        unit = parcel.readValue(RecipeUnitDomain::class.java.classLoader) as? RecipeUnitDomain
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ingredientId)
        parcel.writeInt(recipeId)
        parcel.writeString(item)
        parcel.writeValue(qty)
        parcel.writeValue(maxQty)
        parcel.writeValue(unit)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Ingredient> {
        override fun createFromParcel(parcel: Parcel): Ingredient = Ingredient(parcel)
        override fun newArray(size: Int): Array<Ingredient?> = arrayOfNulls(size)
    }
}