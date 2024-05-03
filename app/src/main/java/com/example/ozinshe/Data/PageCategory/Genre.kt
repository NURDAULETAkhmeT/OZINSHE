package com.example.ozinshe.Data.PageCategory


import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id")
    val id: Int, // 58
    @SerializedName("name")
    val name: String, // Отбасымен көретіндер
    @SerializedName("fileId")
    val fileId: Int, // 661
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/661
    @SerializedName("movieCount")
    val movieCount: Any // null
)