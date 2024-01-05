package com.saad.invitation.extra

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.saad.invitation.views.DEBUG_TAG
import kotlin.math.max
import kotlin.math.min


class MultiTouchImageListener : View.OnTouchListener {
    // These matrices will be used to move and zoom image
    private val matrix: Matrix = Matrix()
    private val savedMatrix: Matrix = Matrix()

    // We can be in one of these 3 states
    private val NONE = 0
    private val DRAG = 1
    private val ZOOM = 2
    private var mode = NONE

    // Remember some things for zooming
    private val start = PointF()
    private val mid = PointF()
    private var oldDist = 1f
    private var d = 0f
    private var newRot = 0f
    private var lastEvent: FloatArray? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val view = v as ImageView
        val parent = view.parent as View
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(DEBUG_TAG, "ActionDown")
                savedMatrix.set(matrix)
                start[event.x] = event.y
                mode = DRAG
                lastEvent = null
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d(DEBUG_TAG, "Pointer Down")

                oldDist = spacing(event)
                if (oldDist > 10f) {
                    savedMatrix.set(matrix)
                    midPoint(mid, event)
                    mode = ZOOM
                }
                lastEvent = FloatArray(4)
                lastEvent!![0] = event.getX(0)
                lastEvent!![1] = event.getX(1)
                lastEvent!![2] = event.getY(0)
                lastEvent!![3] = event.getY(1)
                d = rotation(event)
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
                checkBoundaries(view, parent)
            } else if (mode == ZOOM) {
                val newDist = spacing(event)
                if (newDist > 10f) {
                    matrix.set(savedMatrix)
                    val scale = newDist / oldDist
                    matrix.postScale(scale, scale, mid.x, mid.y)
                }
                if (lastEvent != null && event.pointerCount == 3) {
                    newRot = rotation(event)
                    val r = newRot - d
                    val values = FloatArray(9)
                    matrix.getValues(values)
                    val tx = values[2]
                    val ty = values[5]
                    val sx = values[0]
                    val xc = view.width / 2 * sx
                    val yc = view.height / 2 * sx
                    matrix.postRotate(r, tx + xc, ty + yc)
                }
            }
        }
        view.imageMatrix = matrix
        return true
    }

    /**
     * Determine the space between the first two fingers
     * @param event of movement
     * @return space between fingers
     */
    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point[x / 2] = y / 2
    }

    /**
     * Calculate the degree to be rotated with 3 finger
     * @param event of movement
     * @return degree to be rotated
     */
    private fun rotation(event: MotionEvent): Float {
        val delta_x = (event.getX(0) - event.getX(1)).toDouble()
        val delta_y = (event.getY(0) - event.getY(1)).toDouble()
        val radians = Math.atan2(delta_y, delta_x)
        return Math.toDegrees(radians).toFloat()
    }

    private fun checkBoundaries(imageView: ImageView, parent: View) {
        val drawable = imageView.drawable
        if (drawable != null) {
            val matrixValues = FloatArray(9)
            matrix.getValues(matrixValues)

            val imageWidth = drawable.intrinsicWidth * matrixValues[Matrix.MSCALE_X]
            val imageHeight = drawable.intrinsicHeight * matrixValues[Matrix.MSCALE_Y]

            val parentWidth = parent.width.toFloat()
            val parentHeight = parent.height.toFloat()

            val translationX = matrixValues[Matrix.MTRANS_X]
            val translationY = matrixValues[Matrix.MTRANS_Y]

            val minX = min(0f, parentWidth - imageWidth)
            val minY = min(0f, parentHeight - imageHeight)

            val maxX = max(0f, parentWidth - imageWidth)
            val maxY = max(0f, parentHeight - imageHeight)

            val clampedTranslationX = translationX.coerceIn(minX, maxX)
            val clampedTranslationY = translationY.coerceIn(minY, maxY)

            matrix.postTranslate(
                clampedTranslationX - translationX,
                clampedTranslationY - translationY
            )
            imageView.imageMatrix = matrix
        }
    }

}