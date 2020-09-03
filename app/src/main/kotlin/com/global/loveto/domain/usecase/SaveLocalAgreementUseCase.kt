package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.ResultUseCase
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.domain.model.Agreement
import kotlinx.coroutines.Dispatchers

class SaveLocalAgreementUseCase(
    private val operationRepository: OperationRepository
) : ResultUseCase<OperationEntity, Agreement>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params: OperationEntity): Agreement? {
        return operationRepository.saveLocalAgreement(params)
    }
}