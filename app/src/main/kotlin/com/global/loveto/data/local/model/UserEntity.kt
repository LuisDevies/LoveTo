package com.global.loveto.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.global.loveto.TABLE_USER
import com.global.loveto.domain.model.User

@Entity(tableName = TABLE_USER)
data class UserEntity(
    @PrimaryKey
    val _id: String,
    val active: Boolean,
    val coopId: String,
    val name: String,
    val login: String
)

fun UserEntity.toUser() =
    User(_id = _id, active = active, coopId = coopId, name = name, login = login)