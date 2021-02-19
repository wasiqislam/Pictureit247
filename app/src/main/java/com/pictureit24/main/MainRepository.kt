package com.pictureit24.main


import com.pictureit24.main.network.RemoteService
import com.pictureit24.main.requests.ImageRequest
import io.reactivex.rxjava3.core.Notification
import io.reactivex.rxjava3.core.Observable

//open due testing mock
open class MainRepository(private val remoteService: RemoteService.Endpoints) {


     fun getList(): Observable<List<MainItem>> {
        return ImageRequest(remoteService)
            .execute()
           // .map{ cacheData(it)}


    }

}
