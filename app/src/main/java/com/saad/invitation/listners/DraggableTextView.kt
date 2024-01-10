package com.saad.invitation.listners

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.setPadding
import com.saad.invitation.R
import com.saad.invitation.utils.log

class DraggableTextView(context: Context) : AppCompatTextView(context) {


    init {
        // Set TextView properties
        layoutParams = ViewGroup.LayoutParams(
            200,
            200
        )
        text = "Your Text Here"
        gravity = Gravity.CENTER_VERTICAL
        gravity = Gravity.CENTER
        setBackgroundResource(R.drawable.rounded_border_tv)
        setTextColor(Color.WHITE)
        setPadding(5)

    }


    fun enableDragAndDrop(
        topLeftIcon: View,
        topRightIcon: View,
        bottomRightIcon: View,
        bottomLeftIcon: View,
        bottomCenterIcon: View,
    ) {
        setOnTouchListener(object : OnTouchListener {
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
                            this@DraggableTextView,
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
        textView: DraggableTextView,
        topLeftIcon: View,
        topRightIcon: View,
        bottomRightIcon: View,
        bottomLeftIcon: View,
        bottomCenterIcon: View,

        ) {
        log("onAttach DragTextView")
        updateCornerIconsPositionRelativeTo(
            textView,
            topLeftIcon,
            topRightIcon,
            bottomRightIcon,
            bottomLeftIcon,
            bottomCenterIcon
        )
        textView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            log("Layout changed")
            updateCornerIconsPositionRelativeTo(
                textView, topLeftIcon, topRightIcon, bottomRightIcon,
                bottomLeftIcon,
                bottomCenterIcon
            )
        }
    }

    private fun updateCornerIconsPositionRelativeTo(
        textView: DraggableTextView,
        topLeftIcon: View,
        topRightIcon: View,
        bottomRightIcon: View,
        bottomLeftIcon: View,
        bottomCenterIcon: View,
    ) {

        val x = textView.x
        val y = textView.y


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

