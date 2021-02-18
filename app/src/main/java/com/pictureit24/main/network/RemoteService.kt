package com.pictureit24.main.network

import com.pictureit24.main.MainItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object RemoteService {

    private const val BASE_URL = "https://picsum.photos/"


    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()


    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    fun create(): Endpoints{
        return retrofit.create(Endpoints::class.java)
    }

    interface Endpoints{

        //        @GET("realestate")
        @GET("v2/list?page=2&limit=30000")
        fun getImages() : Observable<List<MainItem>>

    }

}
