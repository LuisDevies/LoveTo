package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.ResultUseCase
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.domain.model.Claim
import kotlinx.coroutines.Dispatchers

class SaveLocalClaimUseCase(
    private val operationRepository: OperationRepository
) : ResultUseCase<OperationEntity, Claim>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params: OperationEntity): Claim? {
        return operationRepository.saveLocalClaim(params)
    }
}