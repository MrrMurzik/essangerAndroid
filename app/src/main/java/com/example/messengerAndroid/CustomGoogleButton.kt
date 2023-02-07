package com.example.messengerAndroid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.graphics.toColor
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.messengerAndroid.databinding.CustomGoogleButtonBinding


private const val RADIUS = 10f

class CustomGoogleButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        typeface = Typeface.create("open_sans", Typeface.NORMAL)
    }

    private val googleIconDrawable = VectorDrawableCompat.create(resources, R.drawable.ic_google_logo, null)

    private val rect = RectF(0f, 0f, 0f, 0f)

    init {
        isClickable = true
        calculateRect()
    }

    @SuppressLint("ResourceAsColor")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.color = Color.WHITE
        canvas.drawRoundRect(rect, RADIUS, RADIUS, paint)

        paint.color = Color.BLACK
        canvas.drawText("GOOGLE", width * 0.52f, height * 0.66f, paint)

        googleIconDrawable?.draw(canvas)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateRect()
        calculateDrawablePos()
        paint.textSize = height / 2f

    }

    private fun calculateDrawablePos() {
        var top = (height * 0.25).toInt()
        var bottom = (height * 0.75).toInt()
        var left = (width * 0.33).toInt()
        var right = (width * 0.39).toInt()

        val topBottomDiagonal = bottom - top
        val leftRightDiagonal = right - left

        if (topBottomDiagonal > leftRightDiagonal) {
            val excessOfBiggerDiagonal = topBottomDiagonal - leftRightDiagonal
            top += excessOfBiggerDiagonal / 2
            bottom -= excessOfBiggerDiagonal / 2
        } else if (topBottomDiagonal < leftRightDiagonal) {
            val excessOfBiggerDiagonal = topBottomDiagonal - leftRightDiagonal
            left += excessOfBiggerDiagonal / 2
            right -= excessOfBiggerDiagonal / 2
        }

        googleIconDrawable?.setBounds(left, top, right, bottom)
    }

    private fun calculateRect() {
        rect.left = 0f
        rect.right = width.toFloat()
        rect.bottom = height.toFloat()
        rect.top = 0f
    }

    private fun convertDpToPx(dp: Float) : Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        )
    }

}