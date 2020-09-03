package com.up.core.base.responseWithMessage

data class ResponseWithMessage<T>(
    val data: T,
    val ok: Boolean,
    val error: String,
    val messageDescription: MessageDescription
)