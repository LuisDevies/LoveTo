package com.global.loveto.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.global.loveto.TABLE_OPERATION
import com.global.loveto.data.local.model.OperationEntity

@Dao
interface OperationDao {

    @Query("SELECT * FROM $TABLE_OPERATION WHERE id = :id")
    suspend fun get(id: String): OperationEntity

    @Query("SELECT * FROM $TABLE_OPERATION")
    suspend fun getAll(): List<OperationEntity>

    @Query("DELETE FROM $TABLE_OPERATION WHERE synced = 1")
    suspend fun deleteAllSynced()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(claim: OperationEntity) : Long

}