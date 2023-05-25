package com.builditcreative.qfonapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.builditcreative.qfonapp.data.AppRepository
import com.builditcreative.qfonapp.data.remote.ApiResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {

    private val _passengerDataResponse = MutableLiveData<ApiResultModel>()
    val passengerDataResponse: LiveData<ApiResultModel> = _passengerDataResponse

    fun getPassenger(page:Int=0) = viewModelScope.launch {
        _passengerDataResponse.postValue(repository.getPassenger(page = page, size = 5))
    }

}