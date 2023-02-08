package com.example.messengerAndroid

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat


private const val RADIUS = 20f
private const val MIN_HEIGHT_DP = 40f
private const val ICON_SIZE = 20f
private const val ICON_TEXT_INTERVAL = 48f
private const val TEXT_SIZE = 18f

class CustomGoogleButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val iconSize = convertDpToPx(ICON_SIZE)
    private val fontFamily = ResourcesCompat.getFont(context, R.font.roboto_medium)
    private var textStartX = 0f
    private var textStartY = 0f
    private val textHeight = convertSpToPx(TEXT_SIZE)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        typeface = fontFamily
    }

    private val googleIconDrawable = VectorDrawableCompat.create(
        resources,
        R.drawable.ic_google_logo,
        null)

    private val buttonRect = RectF(0f, 0f, 0f, 0f)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.AT_MOST -> {
                MeasureSpec.getSize(widthMeasureSpec) / 3
            }
            else -> {
                widthMeasureSpec
            }
        }
        val height = when (heightMode) {
            MeasureSpec.AT_MOST -> {
                convertDpToPx(MIN_HEIGHT_DP)
            }
            else -> {
                heightMeasureSpec
            }
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {

        super.onSizeChanged(w, h, oldw, oldh)
        calculateButtonPos()
        calculateContentPos()
        paint.textSize = textHeight.toFloat()

    }

    private fun calculateButtonPos() {
        buttonRect.left = 0f
        buttonRect.right = width.toFloat()
        buttonRect.bottom = height.toFloat()
        buttonRect.top = 0f
    }

    private fun calculateContentPos() {
        val iconTopPos = (height - iconSize) / 2
        val iconBottomPos = iconSize + iconTopPos

        val iconLeftPos = (width * 0.33).toInt()
        val iconRightPos = iconLeftPos + iconSize

        googleIconDrawable?.setBounds(iconLeftPos, iconTopPos, iconRightPos, iconBottomPos)

        calculateTextStartPos(iconRightPos)
    }

    private fun calculateTextStartPos(iconRightPos: Int) {
        textStartX = iconRightPos + convertDpToPx(ICON_TEXT_INTERVAL).toFloat()
        textStartY = height / 2 + textHeight / 2.5f
    }


    override fun onDraw(canvas: Canvas) {

        paint.color = Color.WHITE
        canvas.drawRoundRect(buttonRect, RADIUS, RADIUS, paint)

        paint.color = Color.BLACK
        canvas.drawText("GOOGLE", textStartX, textStartY, paint)

        googleIconDrawable?.draw(canvas)

    }


    private fun convertDpToPx(dp: Float) : Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
        ).toInt()
    }

    private fun convertSpToPx(sp: Float) : Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, sp, context.resources.displayMetrics
        ).toInt()
    }
}