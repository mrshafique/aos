package com.aos.shafik.retrofit.repository

import com.aos.shafik.commons.ApiException
import com.aos.shafik.commons.GenericStateFlow
import com.aos.shafik.model.AlbumResp
import com.aos.shafik.retrofit.AlbumService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
class AlbumRepo @Inject constructor(private val accountService: AlbumService) {

    /**
     * @Usage: get album data from server and emit AlbumResp model
     * @Author: Shafik Shaikh
     */
    suspend fun getAlbumData(
        url: String,
        method: String,
        album: String,
        apiKey: String,
        format: String
    ): Flow<GenericStateFlow<AlbumResp>> {
        return flow {
            try {
                emit(GenericStateFlow.Loading)
                val albumResp = accountService.getAlbumData(
                    url = url,
                    method = method,
                    album = album,
                    apiKey = apiKey,
                    format = format
                )
                emit(GenericStateFlow.Success(data = albumResp))
            } catch (e: Exception) {
                emit(ApiException.resolveError(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}