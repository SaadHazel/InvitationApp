package com.saad.invitation.learning

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.saad.invitation.utils.log

class CornerListener(private val frameLayout: FrameLayout) : View.OnTouchListener {
    private var scaleFactor = 1.0f
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var initialDistance = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                log("Action Down Corner")
                lastTouchX = event.rawX
                lastTouchY = event.rawY
            }


            MotionEvent.ACTION_MOVE -> {
                log("Action Move Corner")

                val newDistance = calculateSingleFingerMove(event)
                if (initialDistance == 0f) {
                    initialDistance = newDistance
                }

                val scale = newDistance / initialDistance
                scaleFactor *= scale
                //Apply Scaling
                updateFrameLayoutSize()
            }


            MotionEvent.ACTION_UP -> {
                log("Action Up Corner")

            }
        }
        return true
    }

    private fun calculateSingleFingerMove(event: MotionEvent): Float {
        val x = event.getX(0)
        val y = event.getY(0)
        return kotlin.math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun updateFrameLayoutSize() {
        val parent =
            frameLayout.parent as ConstraintLayout // Parent layout is Frame Layout
        val parentWidth = parent.width
        val parentHeight = parent.height

        val newWidth = (frameLayout.width * scaleFactor).toInt()
        val newHeight = (frameLayout.height * scaleFactor).toInt()

        // Ensure that the new size is within the parent bounds
        if (newWidth <= parentWidth && newHeight <= parentHeight) {
            val layoutParams = frameLayout.layoutParams
            layoutParams.width = newWidth
            layoutParams.height = newHeight
            frameLayout.layoutParams = layoutParams
        }
    }

    fun zoomIn(x: Float, y: Float) {

        scaleFactor *= 1.2f // Increase the scale factor by 20%

        applyScale()
    }

    fun zoomOut() {
        scaleFactor /= 1.2f // Decrease the scale factor by 20%

        applyScale()
    }

    private fun applyScale() {

        frameLayout.scaleX = scaleFactor
        frameLayout.scaleY = scaleFactor
    }
}