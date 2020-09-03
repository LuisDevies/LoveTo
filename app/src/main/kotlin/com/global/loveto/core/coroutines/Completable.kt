package com.global.loveto.core.coroutines

sealed class Completable {
    object OnComplete : Completable()
    data class OnError(val throwable: Throwable) : Completable()
    object OnLoading : Completable()
    object OnCancel : Completable()
}
