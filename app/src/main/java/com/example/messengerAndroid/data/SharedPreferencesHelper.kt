package com.example.messengerAndroid.data

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {

    private const val APP_PREFERENCES = "login_preferences"

    private const val NAME = "NAME"



    lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun set(value: String) = prefs.edit { it.putString(NAME, value) }

    fun get() : String = prefs.getString(NAME,"")!!

    fun clearPrefs() {
        prefs.edit().clear().apply()
    }
}