package com.saad.invitation.learning

import android.annotation.SuppressLint
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class ZoomTouchListener(private val textView: TextView, private val iconView: View) :
    View.OnTouchListener {

    private val maxTextSize = 100f
    private val minTextSize = 8f
    private val scaleFactor = 0.2f

    private var initialFingerSpacing = 0f
    private val startPoint = PointF()

    private val iconInitialX = iconView.x
    private val iconInitialY = iconView.y

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v == null || event == null) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startPoint.set(event.rawX, event.rawY)
                initialFingerSpacing = 0f
                return true
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                initialFingerSpacing = getFingerSpacing(event)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 2) {
                    val newFingerSpacing = getFingerSpacing(event)
                    if (initialFingerSpacing != 0f) {
                        val delta = newFingerSpacing - initialFingerSpacing
                        if (delta > 0) {
                            // Zoom in
                            zoomTextSize(true)
                        } else if (delta < 0) {
                            // Zoom out
                            zoomTextSize(false)
                        }
                        initialFingerSpacing = newFingerSpacing
                    }
                } else if (event.pointerCount == 1) {
                    // Drag for translation
                    val translationX = event.rawX - startPoint.x
                    val translationY = event.rawY - startPoint.y
                    textView.translationX += translationX
                    textView.translationY += translationY

                    // Adjust icon position based on text translation
                    iconView.x = iconInitialX + textView.translationX
                    iconView.y = iconInitialY + textView.translationY

                    startPoint.set(event.rawX, event.rawY)
                }
                return true
            }
        }
        return false
    }

    private fun getFingerSpacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return sqrt(x * x + y * y)
    }

    private fun zoomTextSize(zoomIn: Boolean) {
        val currentTextSize = textView.textSize / textView.resources.displayMetrics.scaledDensity
        val newTextSize = if (zoomIn) {
            min(currentTextSize + scaleFactor, maxTextSize)
        } else {
            max(currentTextSize - scaleFactor, minTextSize)
        }
        textView.textSize = newTextSize
    }
}
