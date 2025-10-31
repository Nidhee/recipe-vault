package com.nidhi.recipevault.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.nidhi.recipevault.R
import kotlin.math.max
/**
 * HorizontalStepperView
 *
 * A lightweight, draw-only horizontal stepper that:
 * - Evenly spaces step circles across the view width (no overflow on small screens)
 * - Draws connecting lines between steps
 * - Shows per-step labels below each circle
 * - Distinguishes done, current, and upcoming states by color
 *
 * Public API:
 * - setSteps(titles): provide the list of step titles (e.g., ["Step1", "Step2", "Step3", "Step4"])
 * - setCurrentStep(index): set current step index (0-based)
 *
 * Layout behavior:
 * - Height is measured to fit: circles + label spacing + tallest label + small padding
 * - Text positions are clamped so labels never render outside the view
 *
 * Notes:
 * - Colors default to app theme colors; adjust constants as needed
 * - Keep titles short on small screens to avoid overlap
 */
class HorizontalStepperView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    // Step titles and current index
    private var titles: List<String> = emptyList()
    private var currentStep: Int = 0

    // Sizing (dp/sp) for circles, lines, spacing, and text
    private var circleRadius = dp(10f)
    private var lineHeight = dp(2f)
    private var itemSpacing = dp(24f)
    private var labelTopMargin = dp(8f)

    // Colors (tweak to fit your theme)
    private val activeColor = resolveColor(R.color.colorOnPrimary)
    private val doneColor = resolveColor(R.color.colorOnPrimary)
    private val inactiveColor = resolveColor(R.color.colorOnPrimary).applyAlpha(0.35f)
    private val textColor = resolveColor(R.color.colorOnPrimary)

    // Paint objects reused for drawing (avoid allocations during onDraw)
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = inactiveColor
    }
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        strokeWidth = lineHeight
        color = inactiveColor
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = sp(12f)
    }
    private val textBounds = Rect()

    /**
     * Provide the list of step titles to display below each circle.
     * Calls requestLayout() to recalc height and invalidate() to redraw.
     */
    fun setSteps(stepTitles: List<String>) {
        titles = stepTitles
        requestLayout()
        invalidate()
    }
    /**
     * Set the current step index (0-based). Value is clamped to valid range.
     * Invalidates the view to update colors/lines instantly.
     */
    fun setCurrentStep(stepIndex: Int) {
        currentStep = stepIndex.coerceIn(0, max(0, titles.size - 1))
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Compute label height based on the tallest label text
        var maxTextHeight = 0
        titles.forEach {
            textPaint.getTextBounds(it, 0, it.length, textBounds)
            maxTextHeight = max(maxTextHeight, textBounds.height())
        }
        // Extra padding to avoid clipping on various densities
        val extra = dp(12f) // small safety padding
        val desiredHeight = (circleRadius * 2f + labelTopMargin + maxTextHeight + extra).toInt()
        val w = resolveSize(suggestedMinimumWidth, widthMeasureSpec)
        val h = resolveSize(desiredHeight, heightMeasureSpec)
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (titles.isEmpty()) return

        val total = titles.size

        // Horizontal range for step centers, kept inside padding and circle radius
        val startX = paddingLeft + circleRadius
        val endX = width - paddingRight - circleRadius

        // Vertical center for circles, labels below
        val centerY = paddingTop + circleRadius
        val labelY = (centerY + circleRadius + labelTopMargin + textPaint.textSize)

        // Compute evenly spaced centers across available width
        val centersX = FloatArray(total)
        if (total == 1) {
            centersX[0] = (startX + endX) / 2f
        } else {
            val step = (endX - startX) / (total - 1).toFloat()
            for (i in 0 until total) {
                centersX[i] = startX + i * step
            }
        }

        // Draw connecting lines between circles
        for (i in 0 until total - 1) {
            val fromX = centersX[i] + circleRadius
            val toX = centersX[i + 1] - circleRadius
            linePaint.color = if (i < currentStep) doneColor else inactiveColor
            canvas.drawLine(fromX, centerY.toFloat(), toX, centerY.toFloat(), linePaint)
        }

        // Draw circles and labels for each step
        for (i in 0 until total) {
            circlePaint.color = when {
                i < currentStep -> doneColor // completed
                i == currentStep -> activeColor // current
                else -> inactiveColor // upcoming
            }
            val cx = centersX[i]
            canvas.drawCircle(cx, centerY.toFloat(), circleRadius, circlePaint)

            // Label text, clamped to view bounds so it never overflows off-screen
            val title = titles[i]
            val textWidth = textPaint.measureText(title)
            val minTx = paddingLeft.toFloat()
            val maxTx = width - paddingRight - textWidth
            val tx = (cx - textWidth / 2f).coerceIn(minTx, maxTx)
            canvas.drawText(title, tx, labelY, textPaint)
        }
    }
    // --- Utilities ---
    private fun resolveColor(colorRes: Int): Int =
        ContextCompat.getColor(context, colorRes)

    private fun Int.applyAlpha(fraction: Float): Int {
        val alpha = ((this ushr 24) and 0xFF)
        val newAlpha = (alpha * fraction).toInt().coerceIn(0, 255)
        return (this and 0x00FFFFFF) or (newAlpha shl 24)
    }

    private fun dp(v: Float): Float = v * resources.displayMetrics.density
    private fun sp(v: Float): Float = v * resources.displayMetrics.scaledDensity
}