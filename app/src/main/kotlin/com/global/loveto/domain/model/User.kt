package com.global.loveto.domain.model

import com.global.loveto.data.local.model.UserEntity

data class User(
    val _id: String,
val active: Boolean,
val coopId: String,
val name: String,
val login: String
)

fun User.toUserEntity() = UserEntity(_id = _id,active = active,coopId = coopId, name = name, login = login)