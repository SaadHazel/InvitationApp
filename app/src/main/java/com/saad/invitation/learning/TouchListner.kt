package com.saad.invitation.learning

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView

class TouchListener(context: Context) : OnTouchListener {

    private val matrix = Matrix()
    private val savedMatrix = Matrix()

    private val start = PointF()
    private val mid = PointF()
    private var oldDist = 1f
    private var mode = NONE

    companion object {
        const val NONE = 0
        const val DRAG = 1
        const val ZOOM = 2
        const val ROTATE = 3
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val imageView = v as ImageView

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(debug_tag, "Action Down of icon")
                matrix.set(imageView.imageMatrix)
                savedMatrix.set(matrix)
                start.set(event.x, event.y)
                mode = DRAG
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d(debug_tag, "Pointer Down")

                oldDist = spacing(event)
                if (oldDist > 10f) {
                    savedMatrix.set(matrix)
                    midPoint(mid, event)
                    mode = ZOOM
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                Log.d(debug_tag, "Action Up")

                mode = NONE
            }

            MotionEvent.ACTION_MOVE -> {
                Log.d(debug_tag, "Action Move")

                if (mode == DRAG) {
                    matrix.set(savedMatrix)
                    matrix.postTranslate(event.x - start.x, event.y - start.y)
                } else if (mode == ZOOM) {
                    val newDist = spacing(event)
                    if (newDist > 10f) {
                        matrix.set(savedMatrix)
                        val scale = newDist / oldDist
                        matrix.postScale(scale, scale, mid.x, mid.y)
                    }
                }
            }
        }

        imageView.imageMatrix = matrix
        return true
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return kotlin.math.sqrt(x * x + y * y.toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point.set(x / 2, y / 2)
    }
}
