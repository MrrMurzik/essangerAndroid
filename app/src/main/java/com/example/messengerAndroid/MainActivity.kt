package com.example.messengerAndroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messengerAndroid.databinding.MyProfileBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MyProfileBinding.inflate(layoutInflater)
        binding.tvName.text = intent.getStringExtra("name")
        setContentView(binding.root)
    }
}


//<com.google.android.material.button.MaterialButton
//android:id="@+id/btnGoogle"
//android:layout_width="match_parent"
//android:layout_height="wrap_content"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintBottom_toTopOf="@id/tvOr"
//app:layout_constraintTop_toBottomOf="@id/etPassword"
//app:layout_constraintVertical_chainStyle="packed"
//app:layout_constraintVertical_bias="1"
//android:background="@drawable/slightly_rounded_corner"
//app:backgroundTint="@color/white"
//android:text="@string/google"
//app:icon="@drawable/ic_google_logo"
//android:textColor="@color/black"
//app:iconTint="@null"
//app:iconGravity="textStart"
//app:iconPadding="@dimen/google_icon_padding"
///>