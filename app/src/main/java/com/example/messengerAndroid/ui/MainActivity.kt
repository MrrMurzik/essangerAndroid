package com.example.messengerAndroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.foundation.navigation.Navigator
import com.example.messengerAndroid.ui.signUp.SignUpFragmentDirections
import com.example.messengerAndroid.ui.viewPager.ViewPagerFragmentDirections

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesHelper.init(this)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp() || super.onSupportNavigateUp()



    override fun showSignUpScreen() {
        val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToSignUpFragment()
        launchDestination(direction)

    }


    override fun signOut() {
        SharedPreferencesHelper.clearPrefs()
        showSignUpScreen()
    }

    override fun showViewPager() {
        val direction = SignUpFragmentDirections.actionSignUpFragmentToViewPagerFragment()
        launchDestination(direction)
    }

    override fun showViewDetails(id: String) {
        val direction = ViewPagerFragmentDirections.actionViewPagerFragmentToViewDetailsFragment(   id)
        launchDestination(direction)

    }

    private fun launchDestination(direction: NavDirections) {

        navController.navigate(
            direction,
            navOptions {
                anim {
                    R.anim.slide_in
                    R.anim.slide_out
                    R.anim.slide_in
                    R.anim.slide_out
                }
            }
        )
    }

}