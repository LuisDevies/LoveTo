package com.global.loveto.domain.model

import com.global.loveto.core.enums.Operation
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.data.remote.model.AgreementEntry

data class Agreement(
    val id: Long,
    val walkerId: String,
    val number: String,
    val video : String,
    var synced : Boolean
)

fun Agreement.toOperationEntity() = OperationEntity(
    id = id,
    walkerId = walkerId,
    farmerNumber = number,
    options = mutableListOf(),
    synced = synced,
    operation = Operation.AGREEMENT,
    video = video
)

fun Agreement.toAgreementEntry() = AgreementEntry(
    walkerId = walkerId,
    number = number,
    video = video
)