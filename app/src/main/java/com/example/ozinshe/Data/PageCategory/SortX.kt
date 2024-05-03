package com.example.ozinshe.Data.PageCategory


import com.google.gson.annotations.SerializedName

data class SortX(
    @SerializedName("empty")
    val empty: Boolean, // false
    @SerializedName("sorted")
    val sorted: Boolean, // true
    @SerializedName("unsorted")
    val unsorted: Boolean // false
)