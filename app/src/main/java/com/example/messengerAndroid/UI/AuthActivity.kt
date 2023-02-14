package com.example.messengerAndroid

import android.app.ActivityOptions
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.messengerAndroid.databinding.SignUpBinding


class AuthActivity : AppCompatActivity() {

    private lateinit var binding: SignUpBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE)
        if (preferences.getString(USER_PREFERENCES, "") != "") {
            goNextActivity(preferences.getString(USER_PREFERENCES, "")!!)
        }

        setListeners()

    }

    private fun setListeners() {
        binding.buttonRegister.setOnClickListener { registerNewUser() }
    }

    private fun registerNewUser() {
        val email = binding.inputLayoutEmail.editText?.text.toString()
        val password = binding.inputLayoutPassword.editText?.text.toString()
        Log.d("myLog", "$email")
        Log.d("myLog", "$password")

        if (getValidityEmail(email) && getValidityPassword(password)) {
            val name = getName(email)
            if (binding.checkBoxRememberMe.isChecked) {
                createPreference(name)
            }
            goNextActivity(name)
        } else {
            showError(getValidityEmail(email), getValidityPassword(password))
        }
    }

    private fun showError(validityEmail: Boolean, validityPassword: Boolean) {
        if (!validityEmail) {
           binding.inputLayoutEmail.error = getString(R.string.invalid_email)
        }
        if (!validityPassword) {
            binding.inputLayoutPassword.error = getString(R.string.invalid_password)
        }
    }

    private fun goNextActivity(name: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("name", name)
        ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    private fun createPreference(name: String) {
        preferences.edit()
            .putString(USER_PREFERENCES, name)
            .apply()
    }

    private fun getName(email: String): String {
        val partOfName = email.substring(0, email.indexOf('@'))
        return partOfName.replace(EMAIL_DIVIDER_PATTERN.toRegex(), " ")
    }

    private fun getValidityPassword(password: String): Boolean {
        /*
        https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-
        least-eight-characters-at-least-one-number-a

        Minimum eight characters, at least one uppercase letter,
        one lowercase letter and one number
         */
        val pattern = Regex(PASSWORD_PATTERN)
        return pattern.containsMatchIn(password)
    }

    private fun getValidityEmail(email: String): Boolean {
        // regex for parsing valid email address from regexlib.com
        val pattern = Regex(EMAIL_PATTERN)
        Log.d("myTag", "${pattern.containsMatchIn(email)}")
        return pattern.containsMatchIn(email)
    }

    companion object {
        const val PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"
        const val EMAIL_PATTERN = "([\\w.\\-_]+?@\\w+?\\x2E.+)"
        const val EMAIL_DIVIDER_PATTERN = "[-._]"
        const val USER_PREFERENCES = "USER_PREFERENCES"
    }

}