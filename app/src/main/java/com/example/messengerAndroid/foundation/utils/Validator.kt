package com.example.messengerAndroid.foundation.utils

import android.util.Patterns
import com.example.messengerAndroid.Constants.PASSWORD_PATTERN

object Validator {



    fun getValidityPassword(password: String): Boolean {
        /*
        https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-
        least-eight-characters-at-least-one-number-a

        Minimum eight characters, at least one uppercase letter,
        one lowercase letter and one number
         */
        val pattern = Regex(PASSWORD_PATTERN)
        return pattern.containsMatchIn(password)
    }

    fun getValidityEmail(email: String): Boolean {
        // regex for parsing valid email address from regexlib.com
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}