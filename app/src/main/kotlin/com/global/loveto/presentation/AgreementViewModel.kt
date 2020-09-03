package com.global.loveto.presentation

import androidx.lifecycle.ViewModel
import com.global.loveto.core.extension.LiveResult
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.domain.model.Agreement
import com.global.loveto.domain.usecase.SaveLocalAgreementUseCase
import com.global.loveto.domain.usecase.SaveRemoteAgreementUseCase

class AgreementViewModel(
    private val saveRemoteAgreementUseCase: SaveRemoteAgreementUseCase,
    private val saveLocalAgreementUseCase: SaveLocalAgreementUseCase
) : ViewModel() {

    val agreement = LiveResult<Agreement>()
    val any = LiveResult<Any>()

    fun saveLocalAgreement(agreementToSave : OperationEntity) {
        saveLocalAgreementUseCase.execute(agreement,agreementToSave)
    }

    fun saveRemoteAgreement(agreementToSave: Agreement) {
        saveRemoteAgreementUseCase.execute(any,agreementToSave)
    }

}