package com.example.messengerAndroid.ui.viewPager.myContacts.adapter

import android.graphics.drawable.Drawable

interface BackgroundColorSelector {

    fun setBackgroundSelectionDrawable(): Drawable?

    fun setBackgroundDefaultDrawable(): Drawable?

}