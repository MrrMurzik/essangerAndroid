package com.example.messengerAndroid.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.messengerAndroid.Constants.APP_PREFERENCES
import com.example.messengerAndroid.Constants.EMAIL_KEY
import com.example.messengerAndroid.Constants.PASSWORD_KEY
import com.example.messengerAndroid.Constants.PHONE_KEY
import com.example.messengerAndroid.Constants.PHOTO_KEY
import com.example.messengerAndroid.Constants.USER_NAME_KEY


object SharedPreferencesHelper {

    private lateinit var prefs: SharedPreferences

    fun init(context: Context?) {
        prefs = context?.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE) ?: return
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun setEmail(value: String) = prefs.edit { it.putString(EMAIL_KEY, value)}

    fun getEmail() : String = prefs.getString(EMAIL_KEY,"")?: ""

    fun setPassword(value: String) = prefs.edit { it.putString(PASSWORD_KEY, value)}

    fun getPassword() : String = prefs.getString(PASSWORD_KEY,"")?: ""

    fun setPhone(value: String) = prefs.edit { it.putString(PHONE_KEY, value)}

    fun getPhone() : String = prefs.getString(PHONE_KEY,"")?: ""

    fun setName(value: String) = prefs.edit { it.putString(USER_NAME_KEY, value) }

    fun getName() : String = prefs.getString(USER_NAME_KEY,"")?: ""

    fun setPhoto(value: String) = prefs.edit { it.putString(PHOTO_KEY, value) }

    fun getPhoto() : String = prefs.getString(PHOTO_KEY,"")?: ""

    fun clearPrefs() {
        prefs.edit().clear().apply()
    }


}