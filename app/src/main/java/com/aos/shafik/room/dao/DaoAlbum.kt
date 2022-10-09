package com.aos.shafik.room.dao

import androidx.room.*
import com.aos.shafik.room.entity.EntityAlbum

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@Dao
interface DaoAlbum {
    /**
     * @Usage: insert data in album entity, if we are trying to insert primary key which is already
     * exist in entity then data will skip to insert or update entry
     * @Author: Shafik Shaikh
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAlbum(entityAlbum: EntityAlbum)

    /**
     * @Usage: get list of album from album entity by name in ascending order
     * @Author: Shafik Shaikh
     */
    @Query("SELECT * FROM album ORDER BY name ASC")
    suspend fun getAllAlbum(): List<EntityAlbum>

    /**
     * @Usage: update data in album entity when user capture image from camera
     * @Author: Shafik Shaikh
     */
    @Update
    suspend fun updateAlbum(entityAlbum: EntityAlbum)
}