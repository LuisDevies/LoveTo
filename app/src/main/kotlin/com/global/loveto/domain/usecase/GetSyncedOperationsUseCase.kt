package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.ResultUseCase
import com.global.loveto.data.remote.response.LogsResponse
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.domain.model.Operation
import kotlinx.coroutines.Dispatchers

class GetSyncedOperationsUseCase (
    private val operationRepository: OperationRepository
) : ResultUseCase<String, List<Operation>>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params : String): List<Operation>? {
        return operationRepository.getSyncedOperations(params)
    }
}