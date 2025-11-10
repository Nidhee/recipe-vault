package com.nidhi.recipevault.data.repository

import com.nidhi.recipevault.domain.model.InitStatus
import com.nidhi.recipevault.domain.repository.SystemStatusRepository
import com.nidhi.recipevault.data.local.database.DatabaseErrorStateHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SystemStatusRepositoryImpl @Inject constructor() : SystemStatusRepository {
    override fun observeDatabaseInitStatus(): Flow<InitStatus> {
        return DatabaseErrorStateHolder.errorState.map { err ->
            if (err == null) InitStatus.Ok else InitStatus.Error(err)
        }
    }

}