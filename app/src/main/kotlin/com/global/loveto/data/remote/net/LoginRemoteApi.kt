package com.global.loveto.data.remote.net

import com.global.loveto.core.base.Response
import com.global.loveto.core.coroutines.Completable
import com.global.loveto.data.remote.response.LoginResponse
import com.global.loveto.data.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRemoteApi {

    @POST("app/login")
    fun login(@Body login: LoginRequest): Call<Response<LoginResponse>>

}