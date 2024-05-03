package com.example.ozinshe.Data.CategoryMainMovies


import com.google.gson.annotations.SerializedName

data class Screenshot(
    @SerializedName("id")
    val id: Int, // 161
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/668
    @SerializedName("fileId")
    val fileId: Int, // 668
    @SerializedName("movieId")
    val movieId: Int // 124
)