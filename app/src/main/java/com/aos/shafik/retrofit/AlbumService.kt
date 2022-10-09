package com.aos.shafik.retrofit

import com.aos.shafik.model.AlbumResp
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
interface AlbumService {
    /**
     * @Usage: GET method used; It will get album data from server by passing query parameters named
     * "method", "album", "api_key" and "format"
     * @Author: Shafik Shaikh
     */
    @GET("")
    suspend fun getAlbumData(
        @Url url: String, //http://ws.audioscrobbler.com/2.0/
        @Query("method") method: String, //album.search
        @Query("album") album: String, //aa
        @Query("api_key") apiKey: String, //8f15761ad4472689164c67202aaf3763
        @Query("format") format: String //json
    ): AlbumResp
}