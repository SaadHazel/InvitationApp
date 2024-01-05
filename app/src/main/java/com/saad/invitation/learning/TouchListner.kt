package com.saad.invitation.learning

import android.content.Context
import android.graphics.Matrix
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import kotlin.math.max
import kotlin.math.min

class TouchListener(context: Context) : View.OnTouchListener {

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
        val parent = imageView.parent as View

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                matrix.set(imageView.imageMatrix)
                savedMatrix.set(matrix)
                start.set(event.rawX, event.rawY)
                mode = DRAG
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                oldDist = spacing(event)
                if (oldDist > 10f) {
                    savedMatrix.set(matrix)
                    midPoint(mid, event)
                    mode = ZOOM
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                mode = NONE
            }

            MotionEvent.ACTION_MOVE -> {
                if (mode == DRAG) {
                    matrix.set(savedMatrix)
                    val dx = event.rawX - start.x
                    val dy = event.rawY - start.y
                    matrix.postTranslate(dx, dy)

                    // Check boundaries to prevent dragging out of parent
                    checkBoundaries(imageView, parent)
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
        }
    }
}

