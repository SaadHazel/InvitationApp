package com.saad.invitation.extra

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.saad.invitation.views.DEBUG_TAG

class TextTouchListener : View.OnTouchListener {
    private val matrix = Matrix()
    private val savedMatrix = Matrix()
    private val start = PointF()

    private val NONE = 0
    private val DRAG = 1
    private var mode = NONE
    private var lastEvent: FloatArray? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val view = v as TextView

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(DEBUG_TAG, "ActionDown")

                savedMatrix.set(matrix)
                start.set(event.x, event.y)
                mode = DRAG
                lastEvent = null
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                Log.d(DEBUG_TAG, "ActionPointerUp")

                mode = NONE
                lastEvent = null
            }

            MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                Log.d(DEBUG_TAG, "ActionMove")

                matrix.set(savedMatrix)

                val dx = event.x - start.x
                val dy = event.y - start.y
                matrix.postTranslate(dx, dy)

                // Get the values array from the matrix
                val values = FloatArray(9)
                matrix.getValues(values)

                // Apply the new transformation to the TextView
                view.translationX = values[Matrix.MTRANS_X]
                view.translationY = values[Matrix.MTRANS_Y]
                view.scaleX = values[Matrix.MSCALE_X]
                view.scaleY = values[Matrix.MSCALE_Y]

                start.set(event.x, event.y)
            }
        }

        return true
    }
}



