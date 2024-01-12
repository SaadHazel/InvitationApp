package com.saad.invitation.learning

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.saad.invitation.R
import com.saad.invitation.utils.log

class UpdatedTouchListner(private val onItemClick: (View) -> Unit) : View.OnTouchListener {

    private lateinit var textLayout: View
    private var dotTop: ImageView? = null
    private var lastX: Float = 0f
    private var lastY: Float = 0f
    private var scaleFactor = 1f
    private var initialDistance = 0f

    private var d = 0f
    lateinit var imageview: ImageView


    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val generatedView =
            when (view) {
                is TextView -> view as TextView
                is ImageView -> view as ImageView
                else -> throw IllegalArgumentException("Unsupported view type")
            }
        val parent = generatedView.parent as View
        val allParent = view.parent as? ViewGroup ?: return false

//        val layoutParams = generatedView.layoutParams as ConstraintLayout.LayoutParams

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.rawX
                lastY = event.rawY
                log("lastX: $lastX")
                log("lastY: $lastY")

                resetBackgroundForAllViews(allParent)

                if (generatedView is TextView) {
//                    generatedView.setBackgroundResource(R.drawable.rounded_border_tv)
//                    setTextViewBackground(generatedView)
                    generatedView.setBackgroundResource(R.drawable.rounded_border_tv)

//                    setSelectedTextViewListener()

                } else if (generatedView is ImageView) {
//                    setFrameLayoutBackground(v)
                }
            }


            MotionEvent.ACTION_MOVE -> {
                when (event.pointerCount) {
                    1 -> {
                        /*    val deltaX = event.rawX - lastX
                            val deltaY = event.rawY - lastY

                            // Calculate new position
                            val newX = layoutParams.leftMargin + deltaX
                            val newY = layoutParams.topMargin + deltaY

                            // Adjust position to stay within parent bounds
                            val maxX = parent.width - generatedView.width
                            val maxY = parent.height - generatedView.height

                            layoutParams.leftMargin = newX.coerceIn(0f, maxX.toFloat()).toInt()
                            layoutParams.topMargin = newY.coerceIn(0f, maxY.toFloat()).toInt()
                            // Apply new layout parameters
                            generatedView.layoutParams = layoutParams

                            lastX = event.rawX
                            lastY = event.rawY*/

                        val deltaX = event.rawX - lastX
                        val deltaY = event.rawY - lastY

                        // Calculate new position
                        val newX = view.x + deltaX
                        val newY = view.y + deltaY

                        // Adjust position to stay within parent bounds
                        val maxX = (view.parent as View).width - view.width
                        val maxY = (view.parent as View).height - view.height

                        val clampedX = newX.coerceIn(0f, maxX.toFloat())
                        val clampedY = newY.coerceIn(0f, maxY.toFloat())

                        view.x = clampedX
                        view.y = clampedY

                        lastX = event.rawX
                        lastY = event.rawY
                    }

                    2 -> {
                        val newDistance = calculateFingerDistance(event)

                        if (initialDistance == 0f) {
                            initialDistance = newDistance
                        }

                        val scale = newDistance / initialDistance
                        scaleFactor *= scale

                        // Apply scaling with pivot at the center
                        generatedView.scaleX = scaleFactor
                        generatedView.scaleY = scaleFactor
                        d = rotation(event)
                    }

                    3 -> {
                        val newRot = rotation(event)
                        val deltaRot = newRot - d

                        generatedView.rotation += deltaRot
                    }
                }
            }

            MotionEvent.ACTION_POINTER_UP -> {
                initialDistance = 0f
            }

            MotionEvent.ACTION_UP -> {
                log("Selected Text: ACTION_UP")
                onItemClick.invoke(generatedView)

//                setTextViewBackground(generatedView as TextView)
            }
        }

        return true
    }


    private fun setSelectedTextViewListener() {
        Log.d("myLogcat", "setSelectedTextViewListener: before click ")
        dotTop = textLayout.findViewById<ImageView>(R.id.img_right_top)
        dotTop?.setOnClickListener {
            Log.d("myLogcat", "setSelectedTextViewListener: ")
        }
    }


    private fun calculateFingerDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return kotlin.math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun rotation(event: MotionEvent): Float {
        val delta_x = (event.getX(0) - event.getX(1)).toDouble()
        val delta_y = (event.getY(0) - event.getY(1)).toDouble()
        val radians = Math.atan2(delta_y, delta_x)
        return Math.toDegrees(radians).toFloat()
    }

    private fun resetBackgroundForAllViews(viewGroup: ViewGroup) {
        for (index in 0 until viewGroup.childCount) {
            val child = viewGroup.getChildAt(index)
            resetBackground(child)
        }
    }

    private fun resetBackground(v: View) {
        v.background = null
    }

}
