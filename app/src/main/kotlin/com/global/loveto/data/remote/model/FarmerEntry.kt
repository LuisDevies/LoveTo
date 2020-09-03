package com.global.loveto.data.remote.model

import com.global.loveto.domain.model.Farmer


data class FarmerEntry(
    val _id : String,
    val number: String,
    val name: String,
    val address: String,
    val sqm2: Double
)

fun FarmerEntry.toFarmer() = Farmer(
    _id = _id,
    number = number,
    name = name,
    address = address,
    sqm2 = sqm2
)