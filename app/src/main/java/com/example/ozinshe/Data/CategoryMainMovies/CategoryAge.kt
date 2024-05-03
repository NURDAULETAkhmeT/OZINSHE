package com.example.ozinshe.Data.CategoryMainMovies


import com.google.gson.annotations.SerializedName

data class CategoryAge(
    @SerializedName("id")
    val id: Int, // 5
    @SerializedName("name")
    val name: String, // 16-18
    @SerializedName("fileId")
    val fileId: Int, // 358
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/358
    @SerializedName("movieCount")
    val movieCount: Any // null
)