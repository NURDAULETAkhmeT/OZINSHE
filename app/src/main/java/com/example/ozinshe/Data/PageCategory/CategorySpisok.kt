package com.example.ozinshe.Data.PageCategory


import com.google.gson.annotations.SerializedName

data class CategorySpisok(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("pageable")
    val pageable: Pageable,
    @SerializedName("totalPages")
    val totalPages: Int, // 1
    @SerializedName("totalElements")
    val totalElements: Int, // 2
    @SerializedName("last")
    val last: Boolean, // true
    @SerializedName("size")
    val size: Int, // 20
    @SerializedName("number")
    val number: Int, // 0
    @SerializedName("sort")
    val sort: SortX,
    @SerializedName("numberOfElements")
    val numberOfElements: Int, // 2
    @SerializedName("first")
    val first: Boolean, // true
    @SerializedName("empty")
    val empty: Boolean // false
)