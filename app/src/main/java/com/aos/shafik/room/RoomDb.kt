package com.aos.shafik.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.shafik.room.dao.DaoAlbum
import com.aos.shafik.room.entity.EntityAlbum

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@Database(entities = [EntityAlbum::class], version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {
    /**
     * @Usage: return interface DaoAlbum
     * @Author: Shafik Shaikh
     */
    abstract fun daoAlbum(): DaoAlbum
}