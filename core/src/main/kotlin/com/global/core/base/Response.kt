package com.up.core.base

data class Response<T>(
    val data: T,
    val ok: Boolean,
    val error: String
)