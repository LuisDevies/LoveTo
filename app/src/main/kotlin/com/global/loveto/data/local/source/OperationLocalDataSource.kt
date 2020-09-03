package com.global.loveto.data.local.source

import com.global.loveto.data.local.database.OperationDao
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.data.local.model.toAgreement
import com.global.loveto.data.local.model.toClaim
import com.global.loveto.data.local.model.toOperation
import com.global.loveto.domain.model.Agreement
import com.global.loveto.domain.model.Claim
import com.global.loveto.domain.model.Operation
import com.global.loveto.domain.model.toOperationEntity

class OperationLocalDataSource(
    private val operationDao: OperationDao
) {
    suspend fun getClaim(id: String): Claim {
        return operationDao.get(id).toClaim()
    }

    suspend fun getAgreement(id: String): Agreement {
        return operationDao.get(id).toAgreement()
    }

    suspend fun saveClaim(claim: OperationEntity): Claim {
        claim.id = operationDao.save(claim)
        return claim.toClaim()
    }

    suspend fun saveAgreement(agreement: OperationEntity): Agreement {
        agreement.id = operationDao.save(agreement)
        return agreement.toAgreement()
    }

    suspend fun getOperations(): List<Operation> {
        return operationDao.getAll().map { it.toOperation() }
    }

    suspend fun saveOperations(operations : List<Operation>) {
        operations.map { operationDao.save(it.toOperationEntity()) }
    }

    suspend fun deleteSynced() {
        operationDao.deleteAllSynced()
    }
}