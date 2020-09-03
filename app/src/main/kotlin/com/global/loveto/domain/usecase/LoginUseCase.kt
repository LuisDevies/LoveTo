package com.global.loveto.domain.usecase

import com.global.loveto.core.coroutines.ResultUseCase
import com.global.loveto.data.repository.LoginRepository
import com.global.loveto.data.request.LoginRequest
import com.global.loveto.domain.model.User
import kotlinx.coroutines.Dispatchers

open class LoginUseCase(
    private val loginRepository: LoginRepository
) : ResultUseCase<LoginRequest, User>(
    backgroundContext = Dispatchers.IO,
    foregroundContext = Dispatchers.Main
) {
    override suspend fun executeOnBackground(params: LoginRequest): User? {
        return loginRepository.login(
            params
        )
    }

}
