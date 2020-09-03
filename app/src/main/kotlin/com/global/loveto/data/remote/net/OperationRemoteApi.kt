package com.global.loveto.data.remote.net

import com.global.loveto.core.base.Response
import com.global.loveto.data.remote.model.AgreementEntry
import com.global.loveto.data.remote.model.ClaimEntry
import com.global.loveto.data.remote.response.LogsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OperationRemoteApi {

    @POST("app/claims")
    fun saveClaim(@Body claim : ClaimEntry): Call<Response<Any>>

    @POST("app/uploadAgreement")
    fun saveAgreement(@Body agreement : AgreementEntry): Call<Response<Any>>

    @GET("app/myLogs/{walkerId}")
    fun getOperations(@Path("walkerId") walkerId : String): Call<Response<LogsResponse>>

}