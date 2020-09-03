package com.global.loveto.data.remote.model

import com.global.loveto.domain.model.User

data class UserEntry(
    val _id: String,
    val active: Boolean,
    val coopId: String,
    val name: String,
    val login: String
)

fun UserEntry.toUser() =
    User(_id = _id, active = active, coopId = coopId, name = name, login = login)