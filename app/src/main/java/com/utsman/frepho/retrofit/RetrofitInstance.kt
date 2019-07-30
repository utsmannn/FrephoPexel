package com.utsman.frepho.retrofit

import com.utsman.frepho.base.BASE_URL
import com.utsman.frepho.base.HEADER
import com.utsman.frepho.data.Responses
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RetrofitInstance {

    @Headers(HEADER)
    @GET("v1/curated")
    fun getCuratedPhoto(
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Observable<Responses>

    @Headers(HEADER)
    @GET("v1/search")
    fun getSearchPhoto(
        @Query("query") query: String?,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int
    ): Observable<Responses>

    companion object {

        private fun create(): RetrofitInstance {

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

            return retrofit.create(RetrofitInstance::class.java)
        }

        fun curatedRx(page: Int): Observable<Responses> =
            create().getCuratedPhoto(30, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        fun searchRx(page: Int, query: String?): Observable<Responses> =
            create().getSearchPhoto(query, 30, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}