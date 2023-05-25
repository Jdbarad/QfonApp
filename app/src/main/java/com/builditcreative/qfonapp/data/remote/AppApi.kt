package com.builditcreative.qfonapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppApi {

    @JvmSuppressWildcards
    @GET("qfonapp-passenger/")
    suspend fun getPassenger(@Query("page")page:Int=1, @Query("size")size:Int=5): Response<ApiResultModel>
}