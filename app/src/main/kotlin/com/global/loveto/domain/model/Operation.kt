package com.global.loveto.domain.model

import com.global.loveto.data.local.model.OperationEntity

data class Operation(
    val id: Long?,
    val walkerId: String,
    val farmerNumber: String,
    val options: List<String>,
    val video: String,
    var synced: Boolean,
    val operation: com.global.loveto.core.enums.Operation
)

fun Operation.toOperationEntity() = OperationEntity(
    walkerId = walkerId,
    farmerNumber = farmerNumber,
    options = options,
    video = video,
    synced = synced,
    operation = operation
)

fun Operation.toClaim() = Claim(
    id = id!!,
    walkerId = walkerId,
    number = farmerNumber,
    options = options,
    synced = synced
)

fun Operation.toAgreement() = Agreement(
    id = id!!,
    walkerId = walkerId,
    number = farmerNumber,
    video = video,
    synced = synced
)