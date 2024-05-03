package com.example.ozinshe.Data.Seasons


import com.google.gson.annotations.SerializedName

data class SeasonsList(
    @SerializedName("movieId")
    val movieId: Int, // 123
    @SerializedName("seasons")
    val seasons: List<Season>,
    @SerializedName("seasonNumber")
    val seasonNumber: Int, // 1
    @SerializedName("videoNumber")
    val videoNumber: Int // 20
)