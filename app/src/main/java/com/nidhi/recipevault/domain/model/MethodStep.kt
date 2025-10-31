package com.nidhi.recipevault.domain.model

import android.os.Parcel
import android.os.Parcelable

data class MethodStep(
    val stepId: Int = 0,
    val recipeId: Int,
    val stepOrder: Int,
    val stepDescription: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        stepId = parcel.readInt(),
        recipeId = parcel.readInt(),
        stepOrder = parcel.readInt(),
        stepDescription = parcel.readString() ?: ""
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(stepId)
        parcel.writeInt(recipeId)
        parcel.writeInt(stepOrder)
        parcel.writeString(stepDescription)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MethodStep> {
        override fun createFromParcel(parcel: Parcel): MethodStep = MethodStep(parcel)
        override fun newArray(size: Int): Array<MethodStep?> = arrayOfNulls(size)
    }
}

