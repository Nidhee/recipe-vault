package com.nidhi.recipevault.domain.model

import android.os.Parcel
import com.nidhi.recipevault.domain.model.CuisineDomain
import com.nidhi.recipevault.domain.model.DifficultyLevelDomain
import com.nidhi.recipevault.domain.model.Ingredient
import com.nidhi.recipevault.domain.model.MealTypeDomain
import com.nidhi.recipevault.domain.model.MethodStep

import android.os.Parcelable

data class Recipe(
    val recipeId: Int,
    val name: String,
    val description: String?,
    val prepTimeMinutes: Int?,
    val cookTimeMinutes: Int?,
    val servings: Int?,
    val difficultyLevel: DifficultyLevelDomain?,
    val mealType: MealTypeDomain?,
    val cuisine: CuisineDomain?,
    val thumbnail: String?,
    val ingredients: List<Ingredient>,
    val methodSteps: List<MethodStep>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        recipeId = parcel.readInt(),
        name = parcel.readString() ?: "",
        description = parcel.readString(),
        prepTimeMinutes = parcel.readValue(Int::class.java.classLoader) as? Int,
        cookTimeMinutes = parcel.readValue(Int::class.java.classLoader) as? Int,
        servings = parcel.readValue(Int::class.java.classLoader) as? Int,
        difficultyLevel = parcel.readValue(DifficultyLevelDomain::class.java.classLoader) as? DifficultyLevelDomain,
        mealType = parcel.readValue(MealTypeDomain::class.java.classLoader) as? MealTypeDomain,
        cuisine = parcel.readValue(CuisineDomain::class.java.classLoader) as? CuisineDomain,
        thumbnail = parcel.readString(),
        ingredients = parcel.createTypedArrayList(Ingredient.CREATOR) ?: listOf(),
        methodSteps = parcel.createTypedArrayList(MethodStep.CREATOR) ?: listOf()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(recipeId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeValue(prepTimeMinutes)
        parcel.writeValue(cookTimeMinutes)
        parcel.writeValue(servings)
        parcel.writeValue(difficultyLevel)
        parcel.writeValue(mealType)
        parcel.writeValue(cuisine)
        parcel.writeString(thumbnail)
        parcel.writeTypedList(ingredients)
        parcel.writeTypedList(methodSteps)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe = Recipe(parcel)
        override fun newArray(size: Int): Array<Recipe?> = arrayOfNulls(size)
    }
}