package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.CompletableEmptyUseCase
import com.global.loveto.data.repository.OperationRepository
import kotlinx.coroutines.Dispatchers

class DeleteSyncedUseCase  (
    private val operationRepository: OperationRepository
) : CompletableEmptyUseCase(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground() {
        return operationRepository.deleteSynced()
    }
}