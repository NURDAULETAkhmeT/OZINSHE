package com.example.ozinshe.Data.IDMovies


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("id")
    val id: Int, // 139
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/655
    @SerializedName("fileId")
    val fileId: Int, // 655
    @SerializedName("movieId")
    val movieId: Int // 123
)