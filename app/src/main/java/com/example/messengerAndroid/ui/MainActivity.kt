package com.example.messengerAndroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.NavController
import com.example.messengerAndroid.R
import com.example.messengerAndroid.data.contactsRepository.contactModel.User
import com.example.messengerAndroid.data.preferences.SharedPreferencesHelper
import com.example.messengerAndroid.ui.myContacts.MyContactsFragment
import com.example.messengerAndroid.ui.myContacts.contactDetails.ViewDetailsFragment
import com.example.messengerAndroid.ui.myProfile.MyProfileFragment
import com.example.messengerAndroid.ui.navigation.Navigator
import com.example.messengerAndroid.ui.signUp.SignUpFragment

class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SharedPreferencesHelper.init(this)

        showFirstScreen(savedInstanceState)

    }


    private fun showFirstScreen(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            if (SharedPreferencesHelper.getName().isNotEmpty()) {
                supportFragmentManager.commit {
                    val fragment = MyProfileFragment()
                    add(R.id.fragment_container, fragment)
                }
            } else {
                supportFragmentManager.commit {
                    val fragment = SignUpFragment()
                    add(R.id.fragment_container, fragment)
                }
            }
        }
    }



    override fun showSignUpScreen() {
        launchFragment(SignUpFragment())

    }

    override fun showMyContactsScreen(isFetched: Boolean) {
        launchFragment(MyContactsFragment.getInstance(isFetched))

    }

    override fun showMyProfileScreen() {
        launchFragment(MyProfileFragment())
    }

    override fun signOut() {
        SharedPreferencesHelper.clearPrefs()
        showSignUpScreen()
    }

    override fun showViewDetails(user: User) {
        launchFragment(ViewDetailsFragment.getInstance(user))

    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            addToBackStack(null)
            setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in, R.anim.slide_out)
            replace(R.id.fragment_container, fragment)
        }
    }

}