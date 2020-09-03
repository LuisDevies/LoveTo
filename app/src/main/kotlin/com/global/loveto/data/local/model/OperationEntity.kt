package com.global.loveto.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.global.loveto.TABLE_OPERATION
import com.global.loveto.core.enums.Operation
import com.global.loveto.data.local.database.ListStringConverter
import com.global.loveto.data.local.database.OperationEnumConverter
import com.global.loveto.domain.model.Agreement
import com.global.loveto.domain.model.Claim

@Entity(tableName = TABLE_OPERATION)
data class OperationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    val walkerId: String,
    val farmerNumber: String,
    @TypeConverters(ListStringConverter::class)
    val options: List<String>,
    val video : String,
    val synced: Boolean,
    @TypeConverters(OperationEnumConverter::class)
    val operation : Operation
)

fun OperationEntity.toClaim() = Claim(
    id = id,
    walkerId = walkerId,
    number = farmerNumber,
    options = options,
    synced = synced
)

fun OperationEntity.toAgreement() = Agreement(
    id = id,
    walkerId = walkerId,
    number = farmerNumber,
    video = video,
    synced = synced
)

fun OperationEntity.toOperation() = com.global.loveto.domain.model.Operation(
    id = id,
    walkerId = walkerId,
    farmerNumber = farmerNumber,
    options = options,
    video = video,
    synced = synced,
    operation = operation
)