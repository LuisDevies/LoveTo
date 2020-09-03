package com.global.loveto.domain.model

import com.global.loveto.core.enums.Operation
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.data.remote.model.ClaimEntry

data class Claim(
    val id: Long,
    val walkerId: String,
    val number: String,
    val options: List<String>,
    var synced: Boolean
)

fun Claim.toOperationEntity() = OperationEntity(
    id = id,
    walkerId = walkerId,
    farmerNumber = number,
    options = options,
    synced = synced,
    operation = Operation.CLAIM,
    video = ""
)

fun Claim.toClaimEntry() = ClaimEntry(
    walkerId = walkerId,
    number = number,
    options = options
)