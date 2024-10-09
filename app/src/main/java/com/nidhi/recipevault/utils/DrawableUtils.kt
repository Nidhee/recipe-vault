package com.nidhi.recipevault.com.nidhi.recipevault.utils

import android.content.Context

fun Context.getDrawableId(drawableName: String): Int {
    return resources.getIdentifier(drawableName, "drawable", packageName)
}