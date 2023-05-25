package com.builditcreative.qfonapp.data.remote

import retrofit2.Response

class RemoteRepository(private val appApi: AppApi) {

    suspend fun getPassenger(page:Int,size:Int): Response<ApiResultModel> {
        return appApi.getPassenger(page = page, size = size)
    }

}