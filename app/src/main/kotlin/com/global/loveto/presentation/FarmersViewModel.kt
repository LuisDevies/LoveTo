package com.global.loveto.presentation

import androidx.lifecycle.ViewModel
import com.global.loveto.core.extension.LiveResult
import com.global.loveto.domain.model.Farmer
import com.global.loveto.domain.usecase.GetFarmersUseCase

class FarmersViewModel(
    private val getFarmersUseCase: GetFarmersUseCase
) : ViewModel() {

    val farmers = LiveResult<List<Farmer>>()

    fun getFarmers(walkerId : String) {
        getFarmersUseCase.execute(farmers,walkerId)
    }

}