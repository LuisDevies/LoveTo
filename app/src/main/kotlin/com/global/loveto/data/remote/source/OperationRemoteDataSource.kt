package com.global.loveto.data.remote.source

import com.global.loveto.core.extension.await
import com.global.loveto.data.remote.net.OperationRemoteApi
import com.global.loveto.data.remote.response.LogsResponse
import com.global.loveto.domain.model.Agreement
import com.global.loveto.domain.model.Claim
import com.global.loveto.domain.model.toAgreementEntry
import com.global.loveto.domain.model.toClaimEntry

class OperationRemoteDataSource(
    private val operationRemoteApi: OperationRemoteApi
) {

    suspend fun saveClaim(claim: Claim): Any {
        return operationRemoteApi.saveClaim(claim.toClaimEntry()).await()!!.data
    }

    suspend fun saveAgreement(agreement: Agreement): Any {
        return operationRemoteApi.saveAgreement(agreement.toAgreementEntry()).await()!!.data
    }

    suspend fun getSyncedOperations(walkerId : String) : LogsResponse {
        return operationRemoteApi.getOperations(walkerId).await()!!.data
    }

}