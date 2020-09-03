package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.ResultUseCase
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.domain.model.Claim
import kotlinx.coroutines.Dispatchers

class SaveRemoteClaimUseCase(
    private val operationRepository: OperationRepository
) : ResultUseCase<Claim, Any>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params: Claim): Any? {
        return operationRepository.saveClaim(params)
    }
}