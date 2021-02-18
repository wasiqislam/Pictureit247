package com.pictureit24.main.requests

import com.pictureit24.main.MainItem
import com.pictureit24.main.network.RemoteService
import io.reactivex.rxjava3.core.Observable

class ImageRequest(private val remoteService: RemoteService.Endpoints) {

    fun execute(): Observable<List<MainItem>> {
        return remoteService.getImages()
    }
}
