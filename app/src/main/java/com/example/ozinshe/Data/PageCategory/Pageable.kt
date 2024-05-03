package com.example.ozinshe.Data.PageCategory


import com.google.gson.annotations.SerializedName

data class Pageable(
    @SerializedName("sort")
    val sort: SortX,
    @SerializedName("offset")
    val offset: Int, // 0
    @SerializedName("pageNumber")
    val pageNumber: Int, // 0
    @SerializedName("pageSize")
    val pageSize: Int, // 20
    @SerializedName("paged")
    val paged: Boolean, // true
    @SerializedName("unpaged")
    val unpaged: Boolean // false
)