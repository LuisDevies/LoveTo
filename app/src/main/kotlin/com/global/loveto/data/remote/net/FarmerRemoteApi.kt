package com.global.loveto.data.remote.net

import com.global.loveto.core.base.Response
import com.global.loveto.data.remote.response.FarmersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FarmerRemoteApi {

    @GET("app/getFarmers/{walkerId}")
    fun getFarmers(@Path("walkerId") walkerId : String): Call<Response<FarmersResponse>>

}