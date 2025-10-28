package com.nidhi.recipevault.com.nidhi.recipevault.utils
/**
 * Extension function to capitalize the first letter of a string
 */
fun String.capitalize(): String {
    return this.lowercase().replaceFirstChar { it.uppercaseChar() }
}