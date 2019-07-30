package com.utsman.frepho.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utsman.frepho.base.BaseViewModel
import com.utsman.frepho.base.loge
import com.utsman.frepho.data.NetworkState
import com.utsman.frepho.retrofit.RetrofitInstance

class CategoryViewModel : BaseViewModel() {

    private val networkLiveState = MutableLiveData<NetworkState>()

    fun getCoverCategory(category: String): LiveData<String> {
        val liveUrl = MutableLiveData<String>()
        val random = (1..200).random()
        networkLiveState.postValue(NetworkState.LOADING)
        disposable.add(
                RetrofitInstance.searchRx(random, category)
                        .map { it.photos[1].src.small }
                        .subscribe({
                            networkLiveState.postValue(NetworkState.LOADED)
                            liveUrl.postValue(it)
                        }, {
                            networkLiveState.postValue(NetworkState.error(it.localizedMessage))
                            loge("llllll", it.message.toString())
                            //it.printStackTrace()
                        })
        )

        return liveUrl
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return networkLiveState
    }

}