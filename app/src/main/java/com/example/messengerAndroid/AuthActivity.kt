package com.example.messengerAndroid

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.messengerAndroid.databinding.SignUpBinding

const val USER_PREFERENCES = "USER_PREFERENCES"

class AuthActivity : AppCompatActivity() {

    private lateinit var bind: SignUpBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
//        preferences = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
//        if (preferences.getString(USER_PREFERENCES, "") != "") {
//            goNextActivity(preferences.getString(USER_PREFERENCES, "")!!)
//        }
        super.onCreate(savedInstanceState)
        bind = SignUpBinding.inflate(layoutInflater)
        setContentView(bind.root)
//        bind.checkBoxRememberMe.setOnCheckedChangeListener {
//                _, _ ->
//            if (bind.checkBoxRememberMe.isChecked) {
//                bind.checkBoxRememberMe.setButtonDrawable(R.drawable.ic_radio_button)
//            } else {
//                bind.checkBoxRememberMe.setButtonDrawable(R.drawable.ic_radio_button_empty)
//            }
//        }
//        bind.buttonRegister.setOnClickListener { registerNewUser() }
//    }
//
//    private fun registerNewUser() {
//        val email = bind.editTextEmail.text.toString().lowercase()
//        val password = bind.editTextPassword?.text.toString()
//        if (getValidityEmail(email) && getValidityPassword(password)) {
//            val name = getName(email)
//            if (bind.checkBoxRememberMe.isChecked) {
//                createPreference(name)
//            }
//            goNextActivity(name)
//        } else {
//            showError(getValidityEmail(email), getValidityPassword(password))
//        }
//    }
//
//    private fun showError(validityEmail: Boolean, validityPassword: Boolean) {
//        if (!validityEmail) {
//            bind.editTextEmail.error = getString(R.string.invalid_email)
//        }
//        if (!validityPassword) {
//            bind.editTextPassword?.error = getString(R.string.invalid_password)
//        }
//    }
//
//    private fun goNextActivity(name: String) {
//        val intent = Intent(this, MainActivity::class.java)
//        intent.putExtra("name", name)
//        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
//    }
//
//    private fun createPreference(name: String) {
//        preferences.edit()
//            .putString(USER_PREFERENCES, name)
//            .apply()
//    }
//
//    private fun getName(email: String): String {
//        val partOfName = email.substring(0, email.indexOf('@'))
//        return partOfName.replace("[-._]".toRegex(), " ")
//    }
//
//    private fun getValidityPassword(password: String): Boolean {
//        /*
//        https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-
//        least-eight-characters-at-least-one-number-a
//
//        Minimum eight characters, at least one uppercase letter,
//        one lowercase letter and one number
//         */
//        val pattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
//        println("${pattern.containsMatchIn(password)} pass")
//        return pattern.containsMatchIn(password)
//    }
//
//    private fun getValidityEmail(email: String): Boolean {
//        // regex for parsing valid email address from regexlib.com
//        val pattern = Regex("([\\w.\\-_]+?@\\w+?\\x2E.+)")
//        println("${pattern.containsMatchIn(email)} email")
//        return pattern.containsMatchIn(email)
//    }

}}