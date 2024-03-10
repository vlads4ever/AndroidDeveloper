package com.example.datarepository

import android.content.Context
import android.content.Context.MODE_PRIVATE

private const val PREFERENCE = "preference"
private const val STRING_KEY = "variable"

class Repository (context: Context) {

    private var localString: String? = null
    private val preference = context.getSharedPreferences(PREFERENCE, MODE_PRIVATE)

    fun getText(): String {
        return when {
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference() != null -> getDataFromSharedPreference()!!
            else -> ""
        }
    }

    fun saveText(text: String) {
        localString = text
        preference.edit().putString(STRING_KEY, text).apply()
    }

    fun clearText() {
        localString = null
        preference.edit().clear().apply()
    }

    private fun getDataFromSharedPreference(): String? {
        return preference.getString(STRING_KEY, null)
    }

    private fun getDataFromLocalVariable(): String? {
        return localString
    }
}