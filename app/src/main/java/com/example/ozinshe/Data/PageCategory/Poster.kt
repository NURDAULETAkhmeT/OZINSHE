package com.example.ozinshe.Data.PageCategory


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("id")
    val id: Int, // 129
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/643
    @SerializedName("fileId")
    val fileId: Int, // 643
    @SerializedName("movieId")
    val movieId: Int // 109
)