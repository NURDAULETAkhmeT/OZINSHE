package com.example.ozinshe.Data.Janr


import com.google.gson.annotations.SerializedName

data class janrItem(
    @SerializedName("id")
    val id: Int, // 4
    @SerializedName("name")
    val name: String, // Ойын-сауық
    @SerializedName("fileId")
    val fileId: Int, // 360
    @SerializedName("link")
    val link: String, // http://api.ozinshe.com/core/public/V1/show/360
    @SerializedName("movieCount")
    val movieCount: Int // 9
)