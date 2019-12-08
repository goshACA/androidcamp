package com.example.customprogressbar.custom_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.customprogressbar.R


class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val backgroundPaint = Paint().apply {
        isAntiAlias = true
        color = DEF_COLOR
        style = Paint.Style.FILL
        strokeWidth = DEF_STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
    }

    private val progressPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = DEF_STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
    }

    private var progress: Float = DEF_PROGRESS
    private var startColor: Int = DEF_COLOR
    private var endColor: Int = DEF_COLOR
    private var progressEndX: Float = 0f
    private var strokeWidth: Float = DEF_STROKE_WIDTH
    private var startX = 0f
    private var startY = 0f


    init {
        context.takeIf { attrs != null }?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressView,
            defStyleAttr,
            0
        )?.apply {
            try {
                strokeWidth = getDimension(R.styleable.ProgressView_strokeWidth, DEF_STROKE_WIDTH)
                backgroundPaint.strokeWidth = strokeWidth
                progressPaint.strokeWidth = strokeWidth
                backgroundPaint.color = getColor(R.styleable.ProgressView_backgroundColor, DEF_COLOR)
                progressPaint.color = getColor(R.styleable.ProgressView_progressColor, DEF_COLOR)
                progress = getFloat(R.styleable.ProgressView_progress, DEF_PROGRESS)
                startColor = getColor(R.styleable.ProgressView_progressStartColor, DEF_COLOR)
                endColor = getColor(R.styleable.ProgressView_progressEndColor, DEF_COLOR)
            } finally {
                recycle()
            }
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 12 * strokeWidth //left space at both edges
        val desiredHeight = strokeWidth

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width: Int
        val height: Int

        width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST ->
                desiredWidth.toInt().coerceAtMost(widthSize)
            else ->
                desiredWidth.toInt()
        }

        height = when (heightMode) {
            MeasureSpec.EXACTLY ->
                heightSize
            MeasureSpec.AT_MOST ->
                desiredHeight.toInt().coerceAtMost(heightSize)
            else ->
                desiredHeight.toInt()
        }

        setMeasuredDimension(width, height)
    }


    override fun performClick(): Boolean {
        super.performClick()
        invalidate()
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_MOVE) {
            progress = if (event.x - startX > 0) (event.x - startX).coerceAtMost(100f) / 100 else 0f
            performClick()
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val length = 9f * strokeWidth
        startX = width / 12f
        startY = height / 2f
        progressEndX = measureProgressLength(length) + startX

        progressPaint.shader = LinearGradient(
            startX, 0f,
            progressEndX, 0f,
            startColor, endColor, Shader.TileMode.CLAMP
        )

        canvas?.drawCircle(startX, startY, strokeWidth / 2, backgroundPaint)  //left background edge
        canvas?.drawRect(
            startX, startY - strokeWidth / 2,
            length + startX, startY + strokeWidth / 2, backgroundPaint //background progress line
        )
        canvas?.drawCircle(startX + length, startY, strokeWidth / 2, backgroundPaint) //right background edge

        val progressLength = measureProgressLength(length + progressPaint.strokeWidth)
        if (progressLength >= strokeWidth) {

            canvas?.drawCircle(startX, startY, strokeWidth / 2, progressPaint)
            canvas?.drawRect(
                startX,
                startY - strokeWidth / 2,
                progressLength + startX - strokeWidth,
                startY + strokeWidth / 2,
                progressPaint
            )
            canvas?.drawCircle(
                progressLength + startX - strokeWidth,
                startY,
                strokeWidth / 2,
                progressPaint
            )
        } else {
            canvas?.drawArc(
                startX - strokeWidth / 2, startY - progressLength / 2,
                startX - strokeWidth / 2 + progressLength, startY + progressLength / 2, 90f,
                180f, true, progressPaint
            )
        }

    }

    private fun measureProgressLength(length: Float): Float = length * progress

    companion object {
        private const val DEF_STROKE_WIDTH = 20f
        private const val DEF_COLOR = Color.BLACK
        private const val DEF_PROGRESS = 0f

    }
}