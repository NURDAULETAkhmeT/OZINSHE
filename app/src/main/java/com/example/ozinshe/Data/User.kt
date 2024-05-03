package com.example.ozinshe.Data


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int, // 25381
    @SerializedName("username")
    val username: String, // string124@gmail.com
    @SerializedName("email")
    val email: String, // string124@gmail.com
    @SerializedName("roles")
    val roles: List<String>,
    @SerializedName("accessToken")
    val accessToken: String, // eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHJpbmcxMjRAZ21haWwuY29tIiwiaWF0IjoxNzA2ODA3MDIyLCJleHAiOjE3MzgzNDMwMjJ9.C1eYkgA_X96CgONuDk41U8XSI4U4ROaMguG6urPMXimqEc2JGfgYhaSxNYeBM5xqaSBAbEX1kjsQMmX23BaaKQ
    @SerializedName("tokenType")
    val tokenType: String // Bearer
)