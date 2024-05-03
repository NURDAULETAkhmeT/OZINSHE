package com.example.ozinshe.Data.CategoryAge


import com.google.gson.annotations.SerializedName

data class CategoryAgeItem(
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("name")
    val name: String, // 8-10
    @SerializedName("fileId")
    val fileId: Int, // 353
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/353
    @SerializedName("movieCount")
    val movieCount: Int // 17
)