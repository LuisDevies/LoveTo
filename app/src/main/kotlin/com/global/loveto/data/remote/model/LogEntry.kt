package com.global.loveto.data.remote.model

import com.global.loveto.domain.model.Operation

data class LogEntry(
    val date: String,
    val _id: String,
    val walkerId: String,
    val farmerNumber: String,
    val typeOfLog: String
)

fun LogEntry.toOperation() = Operation(
    id = null,
    walkerId = walkerId,
    farmerNumber = farmerNumber,
    operation = if (typeOfLog.equals(
            com.global.loveto.core.enums.Operation.CLAIM.toString(),
            true
        )
    ) {
        com.global.loveto.core.enums.Operation.CLAIM
    } else {
        com.global.loveto.core.enums.Operation.AGREEMENT
    },
    synced = true,
    options = listOf(),
    video = ""
)