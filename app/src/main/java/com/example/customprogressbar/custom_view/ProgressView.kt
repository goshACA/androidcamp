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
    private fun createPaint() = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        strokeWidth = DEF_STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
    }

    private val backgroundPaint = createPaint()
    private val progressPaint = createPaint()

    private var progress: Float = DEF_PROGRESS
        set(value) {
            field = value
            invalidate()
        }
    private var startColor: Int = DEF_COLOR
    private var endColor: Int = DEF_COLOR
    private var strokeWidth: Float = DEF_STROKE_WIDTH


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
        val desiredWidth = 10 * strokeWidth //left space at both edges
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


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            return true
        }
        if (event?.action == MotionEvent.ACTION_MOVE) {
            progress = if (event.x > 0) (event.x).coerceAtMost(100f) / 100 else 0f
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val halfStrokeWidth = strokeWidth / 2
        canvas?.drawLine(halfStrokeWidth, halfStrokeWidth, width - halfStrokeWidth, halfStrokeWidth, backgroundPaint)
        val progressEndX = measureProgressLength(width.toFloat())
        progressPaint.shader = LinearGradient(
            halfStrokeWidth, halfStrokeWidth,
            progressEndX, halfStrokeWidth,
            startColor, endColor, Shader.TileMode.CLAMP
        )

        if (progressEndX - halfStrokeWidth < 0) {
            canvas?.drawLine(halfStrokeWidth, halfStrokeWidth, width - halfStrokeWidth, halfStrokeWidth, progressPaint)
            canvas?.drawLine(
                halfStrokeWidth + progressEndX,
                halfStrokeWidth,
                width - halfStrokeWidth,
                halfStrokeWidth,
                backgroundPaint
            )
        } else
            canvas?.drawLine(
                halfStrokeWidth,
                halfStrokeWidth,
                (progressEndX - halfStrokeWidth).coerceAtLeast(halfStrokeWidth),
                halfStrokeWidth,
                progressPaint
            )
    }

    private fun measureProgressLength(length: Float): Float = length * progress

    companion object {
        private const val DEF_STROKE_WIDTH = 20f
        private const val DEF_COLOR = Color.BLACK
        private const val DEF_PROGRESS = 0f

    }
}