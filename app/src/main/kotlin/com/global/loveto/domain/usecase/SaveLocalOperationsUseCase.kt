package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.CompletableUseCase
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.domain.model.Operation
import kotlinx.coroutines.Dispatchers

class SaveLocalOperationsUseCase   (
    private val operationRepository: OperationRepository
) : CompletableUseCase<List<Operation>>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params : List<Operation>) {
        return operationRepository.saveLocalOperations(params)
    }
}