package com.global.loveto.data.repository

import com.global.loveto.data.remote.model.toUser
import com.global.loveto.data.remote.source.LoginRemoteDataSource
import com.global.loveto.data.request.LoginRequest
import com.global.loveto.domain.model.User

class LoginRepository(
    private val loginRemoteDataSource: LoginRemoteDataSource
) {
    suspend fun login(loginRequest: LoginRequest): User {
       return loginRemoteDataSource.login(loginRequest).toUser()
    }
}