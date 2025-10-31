package com.nidhi.recipevault.utils

object LogUtils {
    private const val COMMON_TAG = "RecipeApp"

    fun getTag(cls: Class<*>): String {
        return "$COMMON_TAG${cls.simpleName}:"
    }
}
