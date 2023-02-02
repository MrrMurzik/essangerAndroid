package com.example.messengerAndroid

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.messengerAndroid.databinding.CustomGoogleButtonBinding


class CustomGoogleButton(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int,
    defStyleRes: Int
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    private var binding: CustomGoogleButtonBinding

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : this(context, attrs, defStyle, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.custom_google_button, this, true)
        binding = CustomGoogleButtonBinding.bind(this)
        initAttrs(attrs, defStyle, defStyleRes)
    }

    private fun initAttrs(attrs: AttributeSet?, defStyle: Int, defStyleRes: Int) {
        if (attrs == null) return

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CustomGoogleButton,
            defStyle,
            defStyleRes)
        with(binding) {
            val text = typedArray.getString(R.styleable.CustomGoogleButton_text)
            textViewGoogleText.text = text ?: context.getString(R.string.google)

            val srcLogo = typedArray.getResourceId(R.styleable.CustomGoogleButton_logoSrc, R.drawable.ic_google_logo)
            imageViewLogo.setImageResource(srcLogo)

            val textSize = typedArray.getDimension(R.styleable.CustomGoogleButton_textSize, 16f)
            textViewGoogleText.textSize = textSize

            val fontFamily = typedArray.getFont(R.styleable.CustomGoogleButton_fontName)
            textViewGoogleText.typeface = fontFamily

            val textColor = typedArray.getColor(R.styleable.CustomGoogleButton_textColor, Color.BLACK)
            textViewGoogleText.setTextColor(textColor)

            val backgroundColor = typedArray.getColor(R.styleable.CustomGoogleButton_backgroundColor, Color.WHITE)
            root.setBackgroundColor(backgroundColor)

            val allCaps = typedArray.getBoolean(R.styleable.CustomGoogleButton_allCaps, false)
            textViewGoogleText.isAllCaps = allCaps

        }

        typedArray.recycle()
    }

}