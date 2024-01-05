package com.saad.invitation.learning

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import com.saad.invitation.utils.log

class BackgroundListener(private val onItemClick: (View) -> Unit) : View.OnTouchListener {

    private var lastX = 0f
    private var lastY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val parent = view.parent as View
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                log("Action Down")
                lastX = event.rawX
                lastY = event.rawY
            }

            MotionEvent.ACTION_MOVE -> {
                log("Action Move")
                val deltaX = event.rawX - lastX
                val deltaY = event.rawY - lastY

                val newX = view.x + deltaX
                val newY = view.y + deltaY

                // Calculating the bounds of the cardView
                val leftBound = parent.paddingLeft.toFloat()
                val topBound = parent.paddingTop.toFloat()
                val rightBound = (parent.width - parent.paddingRight - view.width).toFloat()
                val bottomBound = (parent.height - parent.paddingBottom - view.height).toFloat()

                // Ensuring the new position is within the bounds
                val clampedX = newX.coerceIn(leftBound, rightBound)
                val clampedY = newY.coerceIn(topBound, bottomBound)

                view.x = clampedX
                view.y = clampedY

                lastX = event.rawX
                lastY = event.rawY

            }


            MotionEvent.ACTION_UP -> {
                log("Action Up")

            }
        }
        return true
    }
}
