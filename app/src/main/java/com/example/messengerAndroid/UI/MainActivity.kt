package com.example.messengerAndroid.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messengerAndroid.databinding.MyProfileBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyProfileBinding.inflate(layoutInflater)
        binding.textViewName?.text = intent.getStringExtra("name")
        setContentView(binding.root)
    }
}
