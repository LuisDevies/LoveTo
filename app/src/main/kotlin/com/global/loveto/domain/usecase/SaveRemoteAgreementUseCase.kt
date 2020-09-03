package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.ResultUseCase
import com.global.loveto.data.repository.OperationRepository
import com.global.loveto.domain.model.Agreement
import kotlinx.coroutines.Dispatchers

class SaveRemoteAgreementUseCase(
    private val operationRepository: OperationRepository
) : ResultUseCase<Agreement, Any>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params: Agreement): Any? {
        return operationRepository.saveAgreement(params)
    }
}