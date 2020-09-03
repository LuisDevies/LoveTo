package com.global.loveto.domain.model

import com.global.loveto.data.local.model.FarmerEntity
import java.io.Serializable

data class Farmer(
    val _id : String = "",
    val number: String = "",
    val name: String = "",
    val address: String = "",
    val sqm2: Double = 0.0
) : Serializable

fun Farmer.toFarmerEntity() = FarmerEntity(
    _id = _id,
    number = number,
    name = name,
    address = address,
    sqm2 = sqm2
)