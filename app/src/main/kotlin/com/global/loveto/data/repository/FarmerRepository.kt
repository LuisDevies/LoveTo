package com.global.loveto.data.repository

import com.global.loveto.data.local.source.FarmerLocalDataSource
import com.global.loveto.data.remote.model.toFarmer
import com.global.loveto.data.remote.source.FarmerRemoteDataSource
import com.global.loveto.domain.model.Farmer

class FarmerRepository (private val farmerRemoteDataSource: FarmerRemoteDataSource,
                        private val farmerLocalDataSource: FarmerLocalDataSource
) {

    suspend fun getRemoteFarmers(walkerId: String): List<Farmer> {
        return farmerRemoteDataSource.getFarmers(walkerId).map { it.toFarmer() }
    }

    suspend fun getLocalFarmers() : List<Farmer> {
        return farmerLocalDataSource.getFarmers()
    }

    suspend fun saveLocalFarmers(farmers : List<Farmer>) {
        farmerLocalDataSource.saveFarmers(farmers)
    }

}