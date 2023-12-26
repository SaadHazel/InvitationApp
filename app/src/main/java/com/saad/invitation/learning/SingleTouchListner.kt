package com.saad.invitation.learning

import android.annotation.SuppressLint
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.GestureDetectorCompat


const val debug_tag = "Gesture"

class SingleTouchListner(
    private val cardView: CardView,
    private val mDetector: GestureDetectorCompat?,
) :
    View.OnTouchListener,
    SimpleOnGestureListener() {
    private var lastX = 0f
    var lastY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event != null) {
            mDetector?.onTouchEvent(event)
        }
        val parent = v?.parent as View

        when (event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(debug_tag, "ActionDown")
                lastX = event.rawX
                lastY = event.rawY
            }

            MotionEvent.ACTION_MOVE -> {
                Log.d(debug_tag, "ActionMove")
                val deltaX = event.rawX - lastX
                val deltaY = event.rawY - lastY

                val newX = v.x + deltaX
                val newY = v.y + deltaY

                // Calculate the bounds of the cardView
                val leftBound = parent.paddingLeft.toFloat()
                val topBound = parent.paddingTop.toFloat()
                val rightBound = (parent.width - parent.paddingRight - v.width).toFloat()
                val bottomBound = (parent.height - parent.paddingBottom - v.height).toFloat()

                // Ensure the new position is within the bounds
                val clampedX = newX.coerceIn(leftBound, rightBound)
                val clampedY = newY.coerceIn(topBound, bottomBound)

                v.x = clampedX
                v.y = clampedY
                lastX = event.rawX
                lastY = event.rawY
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                Log.d(debug_tag, "Action Up")
            }
        }
        return true
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        Log.d(debug_tag, "Single Tab Up")
        
        return super.onSingleTapUp(e)

    }

    override fun onLongPress(e: MotionEvent) {
        super.onLongPress(e)
        Log.d(debug_tag, "onLongPressed from gesture detector")
    }

}