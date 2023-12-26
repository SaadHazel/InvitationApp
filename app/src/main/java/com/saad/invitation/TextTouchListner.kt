package com.saad.invitation

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView

class TextTouchListner : View.OnTouchListener {
    // These matrices will be used to move and zoom image
    private val matrix: Matrix = Matrix()
    private val savedMatrix: Matrix = Matrix()

    // We can be in one of these 3 states
    private val NONE = 0
    private val DRAG = 1
    private var mode = NONE

    // Remember some things for zooming
    private val start = PointF()
    private var lastEvent: FloatArray? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val view = v as TextView
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(DEBUG_TAG, "ActionDown")
                savedMatrix.set(matrix)
                start[event.x] = event.y
                mode = DRAG
                lastEvent = null
            }


            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                Log.d(DEBUG_TAG, "Action_Up")
                mode = NONE
                lastEvent = null
            }

            MotionEvent.ACTION_MOVE -> if (mode == DRAG) {
                Log.d(DEBUG_TAG, "Action_Move")

                matrix.set(savedMatrix)
                val dx = event.x - start.x
                val dy = event.y - start.y
                matrix.postTranslate(dx, dy)
            }
        }
//        view. = matrix
        return true
    }
}