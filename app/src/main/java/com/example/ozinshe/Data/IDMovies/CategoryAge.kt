package com.example.ozinshe.Data.IDMovies


import com.google.gson.annotations.SerializedName

data class CategoryAge(
    @SerializedName("id")
    val id: Int, // 2
    @SerializedName("name")
    val name: String, // 10-12
    @SerializedName("fileId")
    val fileId: Int, // 257
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/257
    @SerializedName("movieCount")
    val movieCount: Any // null
)