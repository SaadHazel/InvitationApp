package com.saad.invitation.listners

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import com.saad.invitation.R
import com.saad.invitation.utils.log

class DraggableImageView(context: Context) : AppCompatImageView(context) {

    init {
        // Set TextView properties
        layoutParams = ViewGroup.LayoutParams(
            200,
            200
        )
        setImageResource(R.drawable.image2)
        setBackgroundResource(R.drawable.rounded_border_tv)
        scaleType = ScaleType.CENTER_CROP
    }


    fun enableDragAndDrop(
        topLeftIcon: View,
        topRightIcon: View,
        bottomRightIcon: View,
        bottomLeftIcon: View,
        bottomCenterIcon: View,
    ) {
        setOnTouchListener(object : View.OnTouchListener {
            private var lastX: Float = 0f
            private var lastY: Float = 0f

            override fun onTouch(view: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        lastX = event.rawX
                        lastY = event.rawY
                    }

                    MotionEvent.ACTION_MOVE -> {
                        attachTo(
                            this@DraggableImageView,
                            topLeftIcon,
                            topRightIcon,
                            bottomRightIcon,
                            bottomLeftIcon,
                            bottomCenterIcon
                        )
                        val deltaX = event.rawX - lastX
                        val deltaY = event.rawY - lastY

                        x += deltaX
                        y += deltaY

                        lastX = event.rawX
                        lastY = event.rawY
                    }
                }
                return true
            }
        })
    }

    fun attachTo(
        imageView: DraggableImageView,
        topLeftIcon: View,
        topRightIcon: View,
        bottomRightIcon: View,
        bottomLeftIcon: View,
        bottomCenterIcon: View,

        ) {
        log("onAttach DragTextView")
        updateCornerIconsPositionRelativeTo(
            imageView,
            topLeftIcon,
            topRightIcon,
            bottomRightIcon,
            bottomLeftIcon,
            bottomCenterIcon
        )
        imageView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            log("Layout changed")
            updateCornerIconsPositionRelativeTo(
                imageView, topLeftIcon, topRightIcon, bottomRightIcon,
                bottomLeftIcon,
                bottomCenterIcon
            )
        }
    }
    
    private fun updateCornerIconsPositionRelativeTo(
        imageView: DraggableImageView,
        topLeftIcon: View,
        topRightIcon: View,
        bottomRightIcon: View,
        bottomLeftIcon: View,
        bottomCenterIcon: View,
    ) {

        val x = imageView.x
        val y = imageView.y


        //TopLeft
        topLeftIcon.x = x - (topLeftIcon.width / 2)
        topLeftIcon.y = y - (topLeftIcon.height / 2)

        //TopRight
        topRightIcon.x = x + width - (topRightIcon.width / 2)
        topRightIcon.y = y - (topRightIcon.height / 2)

        //BottomRight
        bottomRightIcon.x = x + width - (bottomRightIcon.width / 2)
        bottomRightIcon.y = y + height - (bottomRightIcon.height / 2)
        //BottomLeft
        bottomLeftIcon.x = x - (bottomLeftIcon.width / 2)
        bottomLeftIcon.y = y + height - (bottomLeftIcon.height / 2)
        //BottomCenter
        bottomCenterIcon.x = x + width / 2 - (bottomCenterIcon.width / 2)
        bottomCenterIcon.y = y + height - (bottomCenterIcon.height / 2)

    }

}

