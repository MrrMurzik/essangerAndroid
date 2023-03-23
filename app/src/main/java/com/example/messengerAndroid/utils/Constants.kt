package com.example.messengerAndroid.utils

object Constants {

    // constants for Validator
    const val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
    const val EMAIL_DIVIDER_PATTERN = "[-._]"


    // constants for shared preferences

    const val APP_PREFERENCES = "login_preferences"

    const val USER_NAME_KEY = "name"

    // constants for packages

    const val SETTINGS_PACKAGE_SCHEME = "package"

    // constants for savedInstanceState of AddContactDialog

    const val KEY_SAVE_STATE_NAME = "editTextName"
    const val KEY_SAVE_STATE_JOB = "editTextJob"
    const val KEY_SAVE_STATE_PHOTO_URI = "avatarPhotoUri"
    const val KEY_SAVE_STATE_PHONE_NUMBER = "avatarPhotoUri"
    const val KEY_SAVE_STATE_POSTAL_ADDRESS = "avatarPhotoUri"

    // fragment tags

    const val TAG_ADD_CONTACT_DIALOG = "TAG_ADD_CONTACT_DIALOG"

    // number of pages in viewPager

    const val NUM_TABS = 2


}