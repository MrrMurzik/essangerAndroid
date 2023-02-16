package com.example.messengerAndroid.utils

import com.example.messengerAndroid.ui.SignUpActivity

object Validator {

    private const val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
    private const val EMAIL_PATTERN = "([\\w.\\-_]+?@\\w+?\\x2E.+)"
    const val EMAIL_DIVIDER_PATTERN = "[-._]"

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