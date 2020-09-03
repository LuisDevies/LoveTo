package com.global.loveto.presentation

import androidx.lifecycle.ViewModel
import com.global.loveto.core.extension.LiveCompletable
import com.global.loveto.core.extension.LiveResult
import com.global.loveto.data.remote.model.LogEntry
import com.global.loveto.domain.model.Operation
import com.global.loveto.domain.usecase.DeleteSyncedUseCase
import com.global.loveto.domain.usecase.GetLocalOperationsUseCase
import com.global.loveto.domain.usecase.GetSyncedOperationsUseCase
import com.global.loveto.domain.usecase.SaveLocalOperationsUseCase

class OperationsViewModel(
    private val getLocalOperationsUseCase: GetLocalOperationsUseCase,
    private val getSyncedOperationsUseCase: GetSyncedOperationsUseCase,
    private val saveLocalOperationsUseCase: SaveLocalOperationsUseCase,
    private val deleteSyncedUseCase: DeleteSyncedUseCase
) : ViewModel() {

    val operations = LiveResult<List<Operation>>()
    val deleteCompletable = LiveCompletable()
    val localOperationsCompletable = LiveCompletable()
    val remoteOperations = LiveResult<List<Operation>>()

    fun getLocalOperations() {
        getLocalOperationsUseCase.execute(operations)
    }

    fun deleteSyncedOperations() {
        deleteSyncedUseCase.execute(deleteCompletable)
    }

    fun saveLocalOperations(operations : List<Operation>){
        saveLocalOperationsUseCase.execute(localOperationsCompletable,operations)
    }

    fun getSyncedOperations(walkerId : String) {
        getSyncedOperationsUseCase.execute(remoteOperations,walkerId)
    }


}