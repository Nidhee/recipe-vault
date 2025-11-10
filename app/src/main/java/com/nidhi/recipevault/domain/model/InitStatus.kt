package com.nidhi.recipevault.domain.model

sealed class InitStatus {
    data object Ok : InitStatus()
    data class Error(val throwable: Throwable) : InitStatus()
}