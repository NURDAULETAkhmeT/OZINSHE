package com.example.ozinshe.Data.Loading

interface LoadingAdapter {
    var isLoading: Boolean
    fun showLoading()
    fun hideLoading()
}