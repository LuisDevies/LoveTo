package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.ResultUnitUseCase
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.domain.model.Claim
import com.global.loveto.domain.model.Operation
import kotlinx.coroutines.Dispatchers

class GetLocalOperationsUseCase (
    private val operationRepository: OperationRepository
) : ResultUnitUseCase<List<Operation>>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(): List<Operation>? {
        return operationRepository.getLocalOperations()
    }
}