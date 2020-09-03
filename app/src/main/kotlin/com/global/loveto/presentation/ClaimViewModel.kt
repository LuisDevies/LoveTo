package com.global.loveto.presentation

import androidx.lifecycle.ViewModel
import com.global.loveto.core.extension.LiveResult
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.domain.model.Claim
import com.global.loveto.domain.usecase.SaveLocalClaimUseCase
import com.global.loveto.domain.usecase.SaveRemoteClaimUseCase

class ClaimViewModel (
    private val saveRemoteClaimUseCase: SaveRemoteClaimUseCase,
    private val saveLocalClaimUseCase: SaveLocalClaimUseCase
) : ViewModel() {

    val claim = LiveResult<Claim>()
    val any = LiveResult<Any>()

    fun saveLocalClaim(claimToSave : OperationEntity) {
        saveLocalClaimUseCase.execute(claim,claimToSave)
    }

    fun saveRemoteClaim(claimToSave: Claim) {
        saveRemoteClaimUseCase.execute(any,claimToSave)
    }

}