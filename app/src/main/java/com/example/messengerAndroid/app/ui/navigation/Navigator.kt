package com.example.messengerAndroid.app.ui.navigation


interface Navigator {

    fun showSignUpScreen()

    fun signOut()

    fun showViewDetails(id: String)

    fun showViewPager()

}