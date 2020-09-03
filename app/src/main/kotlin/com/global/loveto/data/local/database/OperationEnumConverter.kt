package com.global.loveto.data.local.database

import androidx.room.TypeConverter
import com.global.loveto.core.enums.Operation

class OperationEnumConverter {

    @TypeConverter
    fun fromString(value: String?): Operation? {
        return when(value) {
            "CLAIM" -> Operation.CLAIM
            else -> Operation.AGREEMENT
        }
    }

    @TypeConverter
    fun fromOperation(operation: Operation?): String? {
        return when(operation) {
            Operation.CLAIM -> "CLAIM"
            else -> "AGREEMENT"
        }
    }

}