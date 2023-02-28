package com.example.messengerAndroid.utils

object Constants {

    // constants for Validator
    const val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
    const val EMAIL_DIVIDER_PATTERN = "[-._]"


    // constants for shared preferences

    const val APP_PREFERENCES = "login_preferences"

    // constants for permissions

    const val READ_CONTACTS_REQUEST_CODE = 42

    // constants for intents

    const val IS_FETCHING_REQUIRED_KEY = "fetching key"
    const val USER_NAME_KEY = "name"

    // constants for packages

    const val SETTINGS_PACKAGE_SCHEME = "package"

}