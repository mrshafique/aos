package com.aos.shafik.room.entity

import androidx.annotation.Keep
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@Keep
@Entity(tableName = "album")
data class EntityAlbum(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "artist")
    val artist: String,
    @ColumnInfo(name = "image")
    var image: String? = null
)