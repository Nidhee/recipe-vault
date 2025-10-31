package com.nidhi.recipevault.domain.model

import android.os.Parcel
import android.os.Parcelable

enum class DifficultyLevelDomain : Parcelable{
    EASY, MEDIUM, HARD;
    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    companion object CREATOR : Parcelable.Creator<DifficultyLevelDomain> {
        override fun createFromParcel(parcel: Parcel): DifficultyLevelDomain = entries[parcel.readInt()]
        override fun newArray(size: Int): Array<DifficultyLevelDomain?> = arrayOfNulls(size)
    }
}

enum class CuisineDomain : Parcelable {
    INDIAN, CHINESE, ITALIAN, MEXICAN, AMERICAN, OTHER;
    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    companion object CREATOR : Parcelable.Creator<CuisineDomain> {
        override fun createFromParcel(parcel: Parcel): CuisineDomain = entries[parcel.readInt()]
        override fun newArray(size: Int): Array<CuisineDomain?> = arrayOfNulls(size)
    }
}

enum class MealTypeDomain : Parcelable{
    BREAKFAST, LUNCH, DINNER, SNACK, DRINK;
    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    companion object CREATOR : Parcelable.Creator<MealTypeDomain> {
        override fun createFromParcel(parcel: Parcel): MealTypeDomain = entries[parcel.readInt()]
        override fun newArray(size: Int): Array<MealTypeDomain?> = arrayOfNulls(size)
    }
}

enum class RecipeUnitDomain : Parcelable {
    NOS, CUP, TSP, TBSP, GRAM, PINCH;
    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    companion object CREATOR : Parcelable.Creator<RecipeUnitDomain> {
        override fun createFromParcel(parcel: Parcel): RecipeUnitDomain = entries[parcel.readInt()]
        override fun newArray(size: Int): Array<RecipeUnitDomain?> = arrayOfNulls(size)
    }
}