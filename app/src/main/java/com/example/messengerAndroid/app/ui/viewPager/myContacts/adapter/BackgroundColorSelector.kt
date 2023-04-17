package com.example.messengerAndroid.app.ui.viewPager.myContacts.adapter

import android.graphics.drawable.Drawable

interface BackgroundColorSelector {

    fun setBackgroundSelectionDrawable(): Drawable?

    fun setBackgroundDefaultDrawable(): Drawable?

}