package com.example.ozinshe.Data.LoginRequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserAddressPassword : ViewModel() {
    var tokenType = MutableLiveData<String?>()
    val login = MutableLiveData<String?>()

    fun clearData() {
        tokenType.value = null
        login.value = null
    }
}

