package com.saad.invitation.views.dynamicviews

import android.content.Context
import android.graphics.Color
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class FrameLayoutFactory {

    companion object {
        fun createMainFrameLayout(context: Context): FrameLayout {
            val mainFrameLayout = FrameLayout(context)
            mainFrameLayout.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            return mainFrameLayout
        }

        fun createBorderFrameLayout(context: Context): FrameLayout {
            val borderFrameLayout = FrameLayout(context)
            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams.marginStart = 3
            layoutParams.topMargin = 3
            layoutParams.marginEnd = 3
            layoutParams.bottomMargin = 3
            borderFrameLayout.layoutParams = layoutParams
            borderFrameLayout.setPadding(0, 0, 0, 0)
            borderFrameLayout.setBackgroundColor(Color.LTGRAY) // Set your background drawable
            return borderFrameLayout
        }

        fun createTextView(context: Context): TextView {
            val textView = TextView(context)

            textView.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            return textView
        }

        fun createImageView(context: Context, imageResource: Int, size: Int): ImageView {
            val imageView = ImageView(context)
            imageView.layoutParams = FrameLayout.LayoutParams(size, size)
            imageView.setImageResource(imageResource)
            return imageView
        }

        fun getGravityFromString(gravity: String): Int {
            return when (gravity) {
                "top|start" -> android.view.Gravity.TOP or android.view.Gravity.START
                "top|end" -> android.view.Gravity.TOP or android.view.Gravity.END
                "bottom|end" -> android.view.Gravity.BOTTOM or android.view.Gravity.END
                "bottom|start" -> android.view.Gravity.BOTTOM or android.view.Gravity.START
                "bottom|center" -> android.view.Gravity.BOTTOM or android.view.Gravity.CENTER
                else -> android.view.Gravity.NO_GRAVITY
            }
        }
    }
}
