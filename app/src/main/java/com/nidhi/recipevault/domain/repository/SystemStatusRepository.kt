package com.nidhi.recipevault.domain.repository

import com.nidhi.recipevault.domain.model.InitStatus
import kotlinx.coroutines.flow.Flow

interface SystemStatusRepository {
    fun observeDatabaseInitStatus(): Flow<InitStatus>
}