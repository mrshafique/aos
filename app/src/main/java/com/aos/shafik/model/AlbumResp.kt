package com.aos.shafik.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * @Author: Shafik Shaikh
 * @Date: 09-10-2022
 */
@Keep
data class AlbumResp(
    @field:SerializedName("results")
    val results: Results? = null
)

@Keep
data class Results(
    @field:SerializedName("albummatches")
    val albumMatches: AlbumMatches? = null
)

@Keep
data class AlbumMatches(
    @field:SerializedName("album")
    val album: List<AlbumItem>? = null
)


@Keep
data class AlbumItem(
    @field:SerializedName("image")
    val image: List<ImageItem?>? = null,
    @field:SerializedName("artist")
    val artist: String? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("url")
    val url: String? = null
)

@Keep
data class ImageItem(
    @field:SerializedName("#text")
    val text: String? = null,
    @field:SerializedName("size")
    val size: String? = null
)