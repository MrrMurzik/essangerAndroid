package com.example.messengerAndroid.app.foundation.customView

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout

class DismissableKeyboardConstraintLayout (context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }
}
