package com.example.messengerAndroid.ui.navigation


interface Navigator {

    fun showSignUpScreen()

    fun signOut()

    fun showViewDetails(id: String)

    fun showViewPager()

}