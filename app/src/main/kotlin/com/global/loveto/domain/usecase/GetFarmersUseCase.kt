package com.global.loveto.domain.usecase

import android.net.ConnectivityManager
import com.global.loveto.core.coroutines.ResultUseCase
import com.global.loveto.core.extension.isNetworkAvailable
import com.global.loveto.data.repository.FarmerRepository
import com.global.loveto.domain.model.Farmer
import kotlinx.coroutines.Dispatchers

open class GetFarmersUseCase(
    private val farmerRepository: FarmerRepository,
    private val connectivityManager: ConnectivityManager
) : ResultUseCase<String, List<Farmer>>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params: String): List<Farmer>? {

        return if (connectivityManager.isNetworkAvailable()) {
            val cacheResults = farmerRepository.getRemoteFarmers(
                params
            )
            farmerRepository.saveLocalFarmers(cacheResults)
            cacheResults
        } else {
            farmerRepository.getLocalFarmers()
        }
    }
}