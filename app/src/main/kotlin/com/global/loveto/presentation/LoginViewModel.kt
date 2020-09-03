package com.global.loveto.presentation

import androidx.lifecycle.ViewModel
import com.global.loveto.core.extension.LiveResult
import com.global.loveto.data.request.LoginRequest
import com.global.loveto.domain.model.Farmer
import com.global.loveto.domain.model.User
import com.global.loveto.domain.usecase.GetFarmersUseCase
import com.global.loveto.domain.usecase.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    val userLogged = LiveResult<User>()

    fun login(loginRequest: LoginRequest) {
        loginUseCase.execute(userLogged,loginRequest)
    }

}