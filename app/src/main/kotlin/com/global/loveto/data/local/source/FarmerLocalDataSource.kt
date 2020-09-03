package com.global.loveto.data.local.source

import com.global.loveto.data.local.database.FarmerDao
import com.global.loveto.data.local.model.toFarmer
import com.global.loveto.domain.model.Farmer
import com.global.loveto.domain.model.toFarmerEntity

open class FarmerLocalDataSource(
    private val farmerDao: FarmerDao
) {
    suspend fun getFarmers(): List<Farmer> {
        return farmerDao.get().map { it.toFarmer() }
    }

    suspend fun saveFarmers(farmers: List<Farmer>) {
        farmerDao.save(farmers.map { it.toFarmerEntity() })
    }
}