package com.global.loveto.data.remote.source

import com.global.loveto.core.extension.await
import com.global.loveto.data.remote.model.UserEntry
import com.global.loveto.data.remote.net.LoginRemoteApi
import com.global.loveto.data.request.LoginRequest

open class LoginRemoteDataSource(
    private val loginRemoteApi: LoginRemoteApi
) {

    suspend fun login(loginRequest : LoginRequest): UserEntry {
        return loginRemoteApi.login(loginRequest).await()!!.data.user
    }

}