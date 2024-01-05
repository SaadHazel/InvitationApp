package com.saad.invitation.learning

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.saad.invitation.utils.log

class ResizableView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    val MIN_SIZE = 100
    val MAX_SIZE = 500
    val EDGE_TOUCH_THRESHOLD = 50

    var startX = 0f
    var startY = 0f
    var originalWidth = 0f
    var originalHeight = 0f
    var selectedEdge: Edge? = null

    private val paint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        isAntiAlias = true
        color = resources.getColor(android.R.color.black)
    }

    private val path: Path = Path()

    init {
        setWillNotDraw(false)
        isClickable = true
        isFocusable = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // Draw the circles at the edges
        drawEdgeCircle(canvas, 0f, 0f)
        drawEdgeCircle(canvas, width.toFloat(), 0f)
        drawEdgeCircle(canvas, 0f, height.toFloat())
        drawEdgeCircle(canvas, width.toFloat(), height.toFloat())

        // Draw the line on the border if an edge is selected
        if (selectedEdge != null) {
            drawResizeLine(canvas)
        }
    }

    private fun drawEdgeCircle(canvas: Canvas, x: Float, y: Float) {
        canvas.drawCircle(x, y, EDGE_TOUCH_THRESHOLD.toFloat(), paint)
    }

    private fun drawResizeLine(canvas: Canvas) {
        when (selectedEdge) {
            Edge.TOP -> canvas.drawLine(startX, startY, startX + originalWidth, startY, paint)
            Edge.BOTTOM -> canvas.drawLine(startX, startY, startX + originalWidth, startY, paint)
            Edge.LEFT -> canvas.drawLine(startX, startY, startX, startY + originalHeight, paint)
            Edge.RIGHT -> canvas.drawLine(startX, startY, startX, startY + originalHeight, paint)
            else -> {}
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
                originalWidth = width.toFloat()
                originalHeight = height.toFloat()
                selectedEdge = getSelectedEdge(startX, startY)
            }

            MotionEvent.ACTION_MOVE -> {
                log("Selected edge $selectedEdge")
                if (selectedEdge != null) {
                    val offsetX = event.x - startX
                    val offsetY = event.y - startY

                    // Resize the view based on the drag
                    val newWidth = Math.max(
                        MIN_SIZE.toFloat(),
                        Math.min(originalWidth + offsetX, MAX_SIZE.toFloat())
                    )
                    val newHeight = Math.max(
                        MIN_SIZE.toFloat(),
                        Math.min(originalHeight + offsetY, MAX_SIZE.toFloat())
                    )

                    layoutParams.width = newWidth.toInt()
                    layoutParams.height = newHeight.toInt()
                    requestLayout()

                    invalidate()
                } else {
                    // Move the view based on the drag
                    val deltaX = event.x - startX
                    val deltaY = event.y - startY

                    val newLeft = left + deltaX
                    val newTop = top + deltaY
                    val newRight = newLeft + width
                    val newBottom = newTop + height

                    layout(newLeft.toInt(), newTop.toInt(), newRight.toInt(), newBottom.toInt())

                    startX = event.x
                    startY = event.y
                }
            }

            MotionEvent.ACTION_UP -> {
                selectedEdge = null
            }
        }
        return super.onTouchEvent(event)
    }

    fun getSelectedEdge(x: Float, y: Float): Edge? {
        if (x <= EDGE_TOUCH_THRESHOLD && y <= EDGE_TOUCH_THRESHOLD) {
            return Edge.TOP_LEFT
        } else if (x >= width - EDGE_TOUCH_THRESHOLD && y <= EDGE_TOUCH_THRESHOLD) {
            return Edge.TOP_RIGHT
        } else if (x <= EDGE_TOUCH_THRESHOLD && y >= height - EDGE_TOUCH_THRESHOLD) {
            return Edge.BOTTOM_LEFT
        } else if (x >= width - EDGE_TOUCH_THRESHOLD && y >= height - EDGE_TOUCH_THRESHOLD) {
            return Edge.BOTTOM_RIGHT
        }
        return null
    }

    enum class Edge {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT
    }
}




