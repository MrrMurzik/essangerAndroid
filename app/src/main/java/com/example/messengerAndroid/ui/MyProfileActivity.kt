package com.example.messengerAndroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.messengerAndroid.data.SharedPreferencesHelper
import com.example.messengerAndroid.databinding.ActivityMyProfileBinding
import com.example.messengerAndroid.extensions.cropPhoto

class MyProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        binding.imageViewPicture.cropPhoto()
        binding.textViewName.text = intent.getStringExtra("name")
        setContentView(binding.root)

        setListeners()
        updateBackButtonBehavior()

    }

    private fun updateBackButtonBehavior() {
        val onBackPressedCallback = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                signOutAndDeleteCache()
            }
        }
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun setListeners() {
        binding.buttonContacts.setOnClickListener {
            val intent = Intent(this, MyContactsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSignOut.setOnClickListener {
            signOutAndDeleteCache()
        }

    }

    private fun signOutAndDeleteCache() {
        SharedPreferencesHelper.clearPrefs()
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}
