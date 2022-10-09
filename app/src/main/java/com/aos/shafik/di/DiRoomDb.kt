package com.aos.shafik.di

import android.content.Context
import androidx.room.Room
import com.aos.shafik.room.RoomDb
import com.aos.shafik.room.dao.DaoAlbum
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@Module
@InstallIn(SingletonComponent::class)
class DiRoomDb {
    /**
     * @Usage: create RoomDb obj; Hilt will inject RoomDb obj in provideDaoDam function
     * @Author: Shafik Shaikh
     */
    @Provides
    @Singleton
    fun providesMyDatabase(@ApplicationContext appContext: Context): RoomDb {
        return Room.databaseBuilder(appContext, RoomDb::class.java, "aso")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * @Usage: create DaoAlbum obj, Hilt will inject DaoAlbum in DaoDamRepo
     * @Author: Shafik Shaikh
     */
    @Singleton
    @Provides
    fun provideDaoDam(roomDb: RoomDb): DaoAlbum {
        return roomDb.daoAlbum()
    }
}