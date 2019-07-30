package com.utsman.frepho.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.utsman.frepho.retrofit.RetrofitInstance
import com.utsman.frepho.data.Pexel
import com.utsman.frepho.base.loge
import com.utsman.frepho.base.logi
import com.utsman.frepho.data.NetworkState
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class FrephoDataSource(private val disposable: CompositeDisposable, private val query: String? = null) :
    ItemKeyedDataSource<Long, Pexel>() {

    private var page = 1
    var networkState = MutableLiveData<NetworkState>()

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Pexel>) {
        networkState.postValue(NetworkState.LOADING)
        logi("lllll", "aaaaaaaa")
        if (query == null) {
            disposable.add(
                RetrofitInstance.curatedRx(page)
                    .map { it.photos }
                    .doOnNext { page++ }
                    .doOnNext {
                        logi("tailu", it.size.toString())
                    }
                    .subscribe({ pexels ->
                        callback.onResult(pexels)
                        networkState.postValue(NetworkState.LOADED)
                    }, { e ->
                        networkState.postValue(NetworkState.error(e.message))
                        loge("aaaaaa", e.localizedMessage)
                    })
            )
        } else {
            disposable.add(
                RetrofitInstance.searchRx(page, query)
                    .doOnNext { page++ }
                    .map { it.photos }
                    .doOnNext {
                        logi("tailu", it.size.toString())
                    }
                    .subscribe({ pexels ->
                        callback.onResult(pexels)
                        networkState.postValue(NetworkState.LOADED)
                    }, { e ->
                        loge("aaaaaa", e.message.toString())
                        networkState.postValue(NetworkState.error(e.message))
                    })
            )
        }
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {
        networkState.postValue(NetworkState.LOADING)
        if (query == null) {
            disposable.add(
                RetrofitInstance.curatedRx(page)
                    .map { it.photos }
                    .doOnNext { page++ }
                    .doOnNext { logi("qqqqqqq", page.toString()) }
                    .subscribe({ pexels ->
                        callback.onResult(pexels)
                        networkState.postValue(NetworkState.LOADED)
                    }, { e ->
                        e.printStackTrace()
                        networkState.postValue(NetworkState.error(e.message))
                        loge("aaaaaa", e.localizedMessage)
                    })
            )
        } else {
            disposable.add(
                RetrofitInstance.searchRx(page, query)
                    .doOnNext { page++ }
                    .map { it.photos }
                    .subscribe({ pexels ->
                        callback.onResult(pexels)
                        networkState.postValue(NetworkState.LOADED)
                    }, { e ->
                        loge("aaaaaa", e.message.toString())
                        networkState.postValue(NetworkState.error(e.message))

                    })
            )
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Pexel>) {
    }

    override fun getKey(item: Pexel): Long = item.id
}