package com.utsman.frepho.data.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.utsman.frepho.data.Pexel
import com.utsman.frepho.data.paging.FrephoDataSource
import io.reactivex.disposables.CompositeDisposable

class FrephoDataFactory(private val disposable: CompositeDisposable, private val query: String? = null) : DataSource.Factory<Long, Pexel>() {

    val pexelLiveData = MutableLiveData<FrephoDataSource>()
    override fun create(): DataSource<Long, Pexel> {
        val data = FrephoDataSource(disposable, query)
        pexelLiveData.postValue(data)
        return data
    }
}