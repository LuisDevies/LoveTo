package com.global.loveto.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.global.loveto.DATABASE_VERSION
import com.global.loveto.data.local.model.FarmerEntity
import com.global.loveto.data.local.model.OperationEntity
import com.global.loveto.data.local.model.UserEntity

@Database(
    entities = [
        UserEntity::class, FarmerEntity::class, OperationEntity::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false)
@TypeConverters(ListStringConverter::class,OperationEnumConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun farmerDao(): FarmerDao
    abstract fun operationDao() : OperationDao
}