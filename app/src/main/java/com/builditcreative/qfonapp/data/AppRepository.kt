package com.builditcreative.qfonapp.data

import com.builditcreative.qfonapp.data.local.PassengerDao
import com.builditcreative.qfonapp.data.remote.ApiResultModel
import com.builditcreative.qfonapp.data.remote.RemoteRepository


class AppRepository(
    private val remoteRepository: RemoteRepository,
    private val passengerDao: PassengerDao,
    private val isNetworkAvailable: Boolean
) {

    suspend fun getPassenger(page: Int,size:Int): ApiResultModel {
        if (isNetworkAvailable) {
            val remoteData = remoteRepository.getPassenger(page,size)
            if (remoteData.isSuccessful) {
                remoteData.body()?.let {
                    passengerDao.insertAllPassengers(it.asEntity())
                    return it
                }
            }
        } else {
            return ApiResultModel(data = passengerDao.getAllPassengers().map { it.asData() })
        }
        return ApiResultModel()
    }

}