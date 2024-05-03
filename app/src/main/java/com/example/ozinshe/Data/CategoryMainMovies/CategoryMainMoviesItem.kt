package com.example.ozinshe.Data.CategoryMainMovies


import com.google.gson.annotations.SerializedName

data class CategoryMainMoviesItem(
    @SerializedName("categoryId")
    val categoryId: Int, // 1
    @SerializedName("categoryName")
    val categoryName: String, // ÖZINŞE–де танымал
    @SerializedName("movies")
    val movies: List<Movy>
)