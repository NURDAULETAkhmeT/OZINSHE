package com.example.ozinshe.Data.IDMovies


import com.google.gson.annotations.SerializedName

data class Screenshot(
    @SerializedName("id")
    val id: Int, // 156
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/636
    @SerializedName("fileId")
    val fileId: Int, // 636
    @SerializedName("movieId")
    val movieId: Int // 123
)