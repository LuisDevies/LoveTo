package com.global.loveto.data.remote.source

import com.global.loveto.core.extension.await
import com.global.loveto.data.remote.model.FarmerEntry
import com.global.loveto.data.remote.net.FarmerRemoteApi

open class FarmerRemoteDataSource(
    private val farmerRemoteApi: FarmerRemoteApi
) {

    suspend fun getFarmers(farmerId: String): List<FarmerEntry> {
        return farmerRemoteApi.getFarmers(farmerId).await()!!.data.farmers
    }
}