package com.example.ozinshe.Data.Seasons


import com.google.gson.annotations.SerializedName

data class Season(
    @SerializedName("id")
    val id: Int, // 136
    @SerializedName("movieId")
    val movieId: Int, // 123
    @SerializedName("number")
    val number: Int, // 1
    @SerializedName("videos")
    val videos: List<Video>
)