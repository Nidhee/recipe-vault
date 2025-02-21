package com.nidhi.recipevault.com.nidhi.recipevault.utils

import android.content.Context
import com.nidhi.recipevault.R

fun Context.getDrawableId(drawableName: String): Int {
    return resources.getIdentifier(drawableName, "drawable", packageName)
}

fun Context.getDrawableIdByName(drawableName: String?): Int {
    return drawableName?.let {
     resources.getIdentifier(it, "drawable", packageName)
    } ?: R.drawable.ic_default_thumbnail
}