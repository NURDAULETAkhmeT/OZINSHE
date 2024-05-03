package com.example.ozinshe.Data.ProfileUser


import com.google.gson.annotations.SerializedName

data class UpdateProfile(
    @SerializedName("birthDate")
    val birthDate: String, // 2024-02-29
    @SerializedName("id")
    val id: Int, // 0
    @SerializedName("language")
    val language: String, // string
    @SerializedName("name")
    val name: String, // string
    @SerializedName("phoneNumber")
    val phoneNumber: String // string
)