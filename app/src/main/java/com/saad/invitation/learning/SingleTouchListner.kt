package com.saad.invitation.learning

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.saad.invitation.R
import com.saad.invitation.utils.createBitmapDrawableFromView
import kotlin.math.atan2
import kotlin.math.roundToLong
import kotlin.math.sqrt


const val debug_tag = "Gesture"
var isViewBeingDragged = false


class SingleTouchListner(
//    private val mDetector: GestureDetectorCompat?,
) : View.OnTouchListener
//    ,
//    SimpleOnGestureListener()
{
    private var lastX = 0f
    private var lastY = 0f
    private var initialDistance = 0f
    private var initialRotation = 0f
    private var lastSelectedView: View? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        val parent = v?.parent as View
        val allParent = v.parent as? ViewGroup ?: return false


        when (event?.action?.and(MotionEvent.ACTION_MASK)) {
            MotionEvent.ACTION_DOWN -> {
                initialDistance = 0f
                initialRotation = 0f
                Log.d(debug_tag, "ActionDown")
                lastX = event.rawX
                lastY = event.rawY
                v.background = null
                currentViewForDeletion = v
                resetBackgroundForAllViews(allParent)
                if (v is TextView) {
                    setTextViewBackground(v)
                    lastSelectedView = v
                    isViewBeingDragged = true
                } else if (v is ImageView) {
                    resetBackground(v)
                    setFrameLayoutBackground(v)
                    lastSelectedView = v
                }
            }

            MotionEvent.ACTION_MOVE -> {
                Log.d(debug_tag, "ActionMove")
                val pointerCount = event.pointerCount
                Log.d(debug_tag, "Pointer count: $pointerCount")
                if (pointerCount >= 2) {
                    /*      val x1 = event.getRawX(0)
                          Log.d(debug_tag, "x1: $x1")

                          val y1 = event.getRawY(0)
                          Log.d(debug_tag, "y1: $y1")

                          val x2 = event.getRawX(1)
                          Log.d(debug_tag, "x2: $x2")

                          val y2 = event.getRawY(1)
                          Log.d(debug_tag, "y2: $y2")
      */

                    // Calculating the distance between two pointers for zooming
                    val x = event.getRawX(0) - event.getRawX(1)
                    val y = event.getRawY(0) - event.getRawY(1)
                    val distance = sqrt((x * x + y * y).toDouble()).toFloat()

//                    val distance =
//                        sqrt((x2 - x1).toDouble().pow(2) + (y2 - y1).toDouble().pow(2))
//                            .toFloat()

                    if (initialDistance == 0f) {
                        initialDistance = distance
                    }

                    // Calculate the change in distance
                    val scaleFactor = distance / initialDistance

                    // Zoom the view
                    v.scaleX *= scaleFactor
                    v.scaleY *= scaleFactor

                    // Calculate the rotation angle for rotation
//                    val angleDegrees =
//                        (Math.toDegrees(atan2((y2 - y1).toDouble(), (x2 - x1).toDouble()))
//                            .toFloat())
                    Log.d(
                        debug_tag,
                        "GetrawX: ${event.getRawX(0)} Getx(0): ${event.getX(0)} event.x : ${event.x} getx(1) : ${
                            event.getX(
                                1
                            )
                        }"
                    )
                    val delta_x = (event.getX(0) - event.getX(1)).roundToLong().toDouble()
                    Log.d(debug_tag, "delta_x: $delta_x")

                    val delta_y = (event.getY(0) - event.getY(1)).roundToLong().toDouble()
                    Log.d(debug_tag, "delta_y: $delta_y")

                    val radians = atan2(delta_y, delta_x).toDouble()
                    Log.d(debug_tag, "radians: $radians")

                    val angleDegrees = Math.toDegrees(radians).toFloat()
                    Log.d(debug_tag, "angleDegrees: $angleDegrees")

                    if (initialRotation == 0f) {
                        initialRotation = angleDegrees
                    }

                    Log.d(debug_tag, "Angle: $angleDegrees")

                    Log.d(debug_tag, "initial rotation: $initialRotation")
                    v.rotation += angleDegrees - initialRotation
                    Log.d(debug_tag, "Rotation: ${v.rotation}")
                    // Update initial values for the next move event
                    initialDistance = distance.roundToLong().toFloat()
                    initialRotation = angleDegrees.roundToLong().toFloat()
                    Log.d(debug_tag, "initialDistance: $initialDistance")
                    Log.d(debug_tag, "initialRotation: $initialRotation")

                } else {

                    val deltaX = event.rawX - lastX
                    val deltaY = event.rawY - lastY

                    val newX = v.x + deltaX
                    val newY = v.y + deltaY

                    // Calculating the bounds of the cardView
                    val leftBound = parent.paddingLeft.toFloat()
                    val topBound = parent.paddingTop.toFloat()
                    val rightBound = (parent.width - parent.paddingRight - v.width).toFloat()
                    val bottomBound = (parent.height - parent.paddingBottom - v.height).toFloat()

                    // Ensuring the new position is within the bounds
                    val clampedX = newX.coerceIn(leftBound, rightBound)
                    val clampedY = newY.coerceIn(topBound, bottomBound)

                    v.x = clampedX
                    v.y = clampedY
                    lastX = event.rawX
                    lastY = event.rawY
                }
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d(debug_tag, "Pointer down")

            }

            MotionEvent.ACTION_BUTTON_PRESS -> {
                Log.d(debug_tag, "Button release")
            }

            MotionEvent.ACTION_UP -> {
                Log.d(debug_tag, "Action Up")
                initialDistance = 0f
                initialRotation = 0f
                if (lastSelectedView != null) {
                    isViewBeingDragged = false
//                    resetBackground(lastSelectedView!!)
//                    lastSelectedView = null
                }
            }

            MotionEvent.ACTION_POINTER_UP -> {
                Log.d(debug_tag, "Pointer Up")

            }

            MotionEvent.ACTION_CANCEL -> {
                Log.d(debug_tag, "Action Cancel")
                resetBackground(v)
                isViewBeingDragged = false
            }
        }

        return true
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

    /*   override fun onSingleTapUp(e: MotionEvent): Boolean {
           Log.d(debug_tag, "Single Tab Up")
           currentView?.visibility = View.GONE
           return super.onSingleTapUp(e)
       }

       override fun onLongPress(e: MotionEvent) {
           super.onLongPress(e)
           Log.d(debug_tag, "onLongPressed from gesture detector")
       }*/

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    private fun setTextViewBackground(v: View) {
        val view = v as TextView
        val inflater = LayoutInflater.from(v.context)
        val textLayout = inflater.inflate(R.layout.view_text_editor, null)

        /* val leftTopIcon = textLayout.findViewById<ImageView>(R.id.imgPhotoEditorClose)
         val rightTopIcon = textLayout.findViewById<ImageView>(R.id.img_right_top)
         val rightBottomIcon = textLayout.findViewById<ImageView>(R.id.img_right_bottom)
         val leftBottomIcon = textLayout.findViewById<ImageView>(R.id.img_left_bottom)

         rightTopIcon.setOnTouchListener(ZoomTouchListener(view))
         rightBottomIcon.setOnClickListener {
             Log.d(debug_tag, "RightBottom")
         }
         leftBottomIcon.setOnClickListener {
             Log.d(debug_tag, "LeftBottom")
         }
         leftTopIcon.setOnClickListener {
             Log.d(debug_tag, "LeftTop")
         }*/

        val textViewInLayout = textLayout.findViewById<TextView>(R.id.tvPhotoEditorText)
        textViewInLayout.text = view.text

        val prevSize = view.textSize
        val currentSize = textViewInLayout.textSize
        val finalSize = prevSize - currentSize

        Log.d(debug_tag, "Final Size: $finalSize")
        textViewInLayout.setTextColor(Color.BLUE)
        textViewInLayout.textSize = finalSize

        textViewInLayout.visibility = View.INVISIBLE
        v.background = createBitmapDrawableFromView(textLayout)
    }

    @SuppressLint("InflateParams")
    private fun setFrameLayoutBackground(v: View) {

        val view = v as ImageView
        val inflater = LayoutInflater.from(v.context)
        val frameLayout = inflater.inflate(R.layout.view_photo_editor, null)

        Log.d(debug_tag, "ViewDrawable: ${view.drawable}")
        val imageViewInLayout = frameLayout.findViewById<ImageView>(R.id.imgPhotoEditorImage)
        imageViewInLayout.setImageDrawable(view.drawable)

        Log.d(debug_tag, "ImageViewInLayout: ${imageViewInLayout.drawable}")

        imageViewInLayout.visibility = View.INVISIBLE

        frameLayout.background = createBitmapDrawableFromView(frameLayout)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var currentViewForDeletion: View? = null
    }

}