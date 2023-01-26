package com.example.level2.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.level2.R
import com.example.level2.model.User

fun ImageView.addPhoto(user: User) {
    if (user.photo.isNotBlank()) {
        Glide.with(this.context)
            .load(user.photo)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_sentiment_very_satisfied_24)
            .error(R.drawable.ic_baseline_sentiment_very_satisfied_24)
            .into(this)
    } else {
        this.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24)
    }
}