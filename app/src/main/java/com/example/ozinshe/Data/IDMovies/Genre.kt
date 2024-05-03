package com.example.ozinshe.Data.IDMovies


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int, // 4
    @SerializedName("name")
    val name: String, // Ойын-сауық
    @SerializedName("fileId")
    val fileId: Int, // 360
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/360
    @SerializedName("movieCount")
    val movieCount: Any // null
)