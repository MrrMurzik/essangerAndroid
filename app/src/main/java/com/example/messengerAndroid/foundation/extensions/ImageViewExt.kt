package com.example.messengerAndroid.foundation.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.messengerAndroid.R

fun ImageView.addPhoto(photoUri: String) {
    if (photoUri.isNotBlank()) {
        Glide.with(this)
            .load(photoUri)
            .circleCrop()
            .placeholder(R.drawable.ic_baseline_sentiment_very_satisfied_24)
            .error(R.drawable.ic_baseline_sentiment_very_satisfied_24)
            .into(this)
    } else {
        this.setImageResource(R.drawable.ic_baseline_sentiment_very_satisfied_24)
    }
}

fun ImageView.cropPhoto(res: Int) {
    Glide.with(this)
        .load(res)
        .circleCrop()
        .placeholder(R.drawable.ic_baseline_sentiment_very_satisfied_24)
        .error(R.drawable.ic_baseline_sentiment_very_satisfied_24)
        .into(this)
}