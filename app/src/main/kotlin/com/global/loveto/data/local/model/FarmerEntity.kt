package com.global.loveto.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.global.loveto.TABLE_FARMER
import com.global.loveto.domain.model.Farmer

@Entity(tableName = TABLE_FARMER)
data class FarmerEntity(
    @PrimaryKey
    val _id: String,
    val number: String,
    val name: String,
    val address: String,
    val sqm2: Double
)

fun FarmerEntity.toFarmer() = Farmer(
    _id = _id,
    number = number,
    name = name,
    address = address,
    sqm2 = sqm2
)