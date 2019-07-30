package com.utsman.frepho.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.utsman.frepho.base.BaseViewModel
import com.utsman.frepho.base.configPaged
import com.utsman.frepho.base.logi
import com.utsman.frepho.data.NetworkState
import com.utsman.frepho.data.Pexel
import com.utsman.frepho.data.factory.FrephoDataFactory
import com.utsman.frepho.data.paging.FrephoDataSource

class MainViewModel : BaseViewModel() {

    private var frephoDataFactory: FrephoDataFactory? = null

    fun getCurated() : LiveData<PagedList<Pexel>> {
        frephoDataFactory = FrephoDataFactory(disposable)
        return LivePagedListBuilder(frephoDataFactory!!, configPaged(4)).build()
    }

    fun getSearch(query: String): LiveData<PagedList<Pexel>> {
        frephoDataFactory = FrephoDataFactory(disposable, query)
        return LivePagedListBuilder(frephoDataFactory!!, configPaged(4)).build()
    }

    fun getLoader(): LiveData<NetworkState> = Transformations.switchMap<FrephoDataSource, NetworkState>(
            frephoDataFactory?.pexelLiveData!!
    ) { it.networkState }
}