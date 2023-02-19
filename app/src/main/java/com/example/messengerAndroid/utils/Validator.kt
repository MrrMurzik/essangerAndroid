package com.example.messengerAndroid.utils

import com.example.messengerAndroid.utils.Constants.PASSWORD_PATTERN
import com.example.messengerAndroid.utils.Constants.EMAIL_PATTERN

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
        val pattern = Regex(EMAIL_PATTERN)
        return pattern.containsMatchIn(email)
    }

}