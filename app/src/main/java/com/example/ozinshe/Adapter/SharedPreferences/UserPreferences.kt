package com.example.ozinshe.Adapter.SharedPreferences

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveCredentials(username: String, password: String) {
        preferences.edit().putString(KEY_USERNAME, username).apply()
        preferences.edit().putString(KEY_PASSWORD, password).apply()
    }

    fun getUsername(): String? {
        return preferences.getString(KEY_USERNAME, null)
    }

    fun getPassword(): String? {
        return preferences.getString(KEY_PASSWORD, null)
    }

    fun clearPreferences() {
        preferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_LANGUAGE = "language"
        private const val KEY_NIGHT_MODE = "night_mode"
    }


    fun saveLanguage(languageCode: String) {
        preferences.edit().putString(KEY_LANGUAGE, languageCode).apply()
    }

    fun getLanguage(): String {
        return preferences.getString(KEY_LANGUAGE, "en") ?: "en"
    }

    // Метод для сохранения состояния Switch
    fun saveNightMode(isNightMode: Boolean) {
        preferences.edit().putBoolean(KEY_NIGHT_MODE, isNightMode).apply()
    }

    // Метод для получения состояния Switch
    fun getNightMode(): Boolean {
        return preferences.getBoolean(KEY_NIGHT_MODE, false)
    }


}
