package com.global.loveto.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.global.loveto.TABLE_USER
import com.global.loveto.data.local.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM $TABLE_USER WHERE _id = :userId")
    suspend fun get(userId: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg user: UserEntity)

    @Query("DELETE FROM $TABLE_USER")
    suspend fun deleteAllUser()
}