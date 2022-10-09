package com.aos.shafik.room.repository

import com.aos.shafik.commons.GenericStateFlow
import com.aos.shafik.room.dao.DaoAlbum
import com.aos.shafik.room.entity.EntityAlbum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
class DaoAlbumRepo @Inject constructor(private val daoDam: DaoAlbum) {
    /**
     * @Usage: insert data in album entity
     * @Author: Shafik Shaikh
     */
    suspend fun insertAlbum(entityAlbum: EntityAlbum) {
        daoDam.insertAlbum(entityAlbum = entityAlbum)
    }

    /**
     * @Usage: get list of album from album entity
     * @Author: Shafik Shaikh
     */
    suspend fun getAllAlbum(): Flow<GenericStateFlow<ArrayList<EntityAlbum>>> {
        return flow {
            emit(GenericStateFlow.Loading)
            emit(GenericStateFlow.Success(data = daoDam.getAllAlbum() as ArrayList))
        }.flowOn(Dispatchers.IO)
    }

    /**
     * @Usage: update data in album entity
     * @Author: Shafik Shaikh
     */
    suspend fun updateAlbum(entityAlbum: EntityAlbum) {
        daoDam.updateAlbum(entityAlbum = entityAlbum)
    }
}