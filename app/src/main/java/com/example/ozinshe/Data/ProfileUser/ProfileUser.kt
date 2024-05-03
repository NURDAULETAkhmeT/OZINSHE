package com.example.ozinshe.Data.ProfileUser

data class ProfileUser(
    val birthDate: String,
    val id: Int,
    val language: String,
    var name: String,
    val phoneNumber: String,
    val user: UserX
)