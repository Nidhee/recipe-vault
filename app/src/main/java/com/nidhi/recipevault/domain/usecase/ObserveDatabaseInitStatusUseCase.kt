package com.nidhi.recipevault.domain.usecase

import com.nidhi.recipevault.domain.model.InitStatus
import com.nidhi.recipevault.domain.repository.SystemStatusRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveDatabaseInitStatusUseCase @Inject constructor(private val repo: SystemStatusRepository) {
    operator fun invoke(): Flow<InitStatus> = repo.observeDatabaseInitStatus()

}