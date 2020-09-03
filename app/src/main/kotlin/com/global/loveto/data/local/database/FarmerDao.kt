package com.global.loveto.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.global.loveto.TABLE_FARMER
import com.global.loveto.data.local.model.FarmerEntity

@Dao
interface FarmerDao {

    @Query("SELECT * FROM $TABLE_FARMER")
    suspend fun get(): List<FarmerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(entities: List<FarmerEntity>)

    @Query("DELETE FROM $TABLE_FARMER")
    suspend fun delete()

}