package com.global.loveto.data.repository

import com.global.loveto.core.coroutines.Completable
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.data.local.source.OperationLocalDataSource
import com.global.loveto.data.remote.model.toOperation
import com.global.loveto.data.remote.response.LogsResponse
import com.global.loveto.data.remote.source.OperationRemoteDataSource
import com.global.loveto.domain.model.Agreement
import com.global.loveto.domain.model.Claim
import com.global.loveto.domain.model.Operation

class OperationRepository (private val operationRemoteDataSource: OperationRemoteDataSource,
                           private val operationLocalDataSource: OperationLocalDataSource
) {

    suspend fun saveClaim(claim : Claim): Any {
        return operationRemoteDataSource.saveClaim(claim)
    }

    suspend fun saveAgreement(agreement: Agreement): Any {
        return operationRemoteDataSource.saveAgreement(agreement)
    }

    suspend fun getLocalOperations() : List<Operation> {
        return operationLocalDataSource.getOperations()
    }

    suspend fun saveLocalClaim(claim: OperationEntity) : Claim {
        return operationLocalDataSource.saveClaim(claim)
    }

    suspend fun saveLocalAgreement(agreement: OperationEntity) : Agreement {
        return operationLocalDataSource.saveAgreement(agreement)
    }

    suspend fun saveLocalOperations(operations: List<Operation>) {
        operationLocalDataSource.saveOperations(operations)
    }

    suspend fun getSyncedOperations(walkerId : String) : List<Operation> {
        return operationRemoteDataSource.getSyncedOperations(walkerId).logs.map { it.toOperation() }
    }

    suspend fun deleteSynced() {
        operationLocalDataSource.deleteSynced()
    }

}