package com.saad.invitation.learning

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.saad.invitation.R
import com.saad.invitation.utils.log

class CustomConstraintLayout(context: Context) : ConstraintLayout(context) {

    init {
        createLayout()
    }

    private fun createLayout() {
        val frmBorderText = createFrameLayout()
        val tvPhotoEditorText = createTextView()
        val imgPhotoEditorClose = createImageView(R.drawable.dot)
        val imgRightTopText = createImageView(R.drawable.dot)
        val imgRightBottom = createImageView(R.drawable.dot)
        val imgLeftBottom = createImageView(R.drawable.dot)
        val imgRotate = createImageView(R.drawable.round_rotate_90_degrees_ccw_24)
        val layoutParams = ConstraintLayout.LayoutParams(
            500,
            500
        )
        this.setBackgroundColor(Color.BLUE)
        this.layoutParams = layoutParams
        // Add views to the ConstraintLayout
        addView(frmBorderText)

        addView(imgRotate)
        frmBorderText.addView(tvPhotoEditorText)
        addView(imgPhotoEditorClose)
        addView(imgRightTopText)
        addView(imgRightBottom)
        addView(imgLeftBottom)

        // Create constraints
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        // Constraint for frmBorderText
        constraintSet.connect(
            frmBorderText.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            frmBorderText.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            frmBorderText.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            frmBorderText.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )

        // Constraints for imgPhotoEditorClose
        constraintSet.connect(
            imgPhotoEditorClose.id,
            ConstraintSet.TOP,
            frmBorderText.id,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            imgPhotoEditorClose.id,
            ConstraintSet.START,
            frmBorderText.id,
            ConstraintSet.START
        )

        // Constraints for imgRightTopText
        constraintSet.connect(
            imgRightTopText.id,
            ConstraintSet.TOP,
            frmBorderText.id,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            imgRightTopText.id,
            ConstraintSet.END,
            frmBorderText.id,
            ConstraintSet.END
        )

        // Constraints for imgRightBottom
        constraintSet.connect(
            imgRightBottom.id,
            ConstraintSet.BOTTOM,
            frmBorderText.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imgRightBottom.id,
            ConstraintSet.END,
            frmBorderText.id,
            ConstraintSet.END
        )

        // Constraints for imgLeftBottom
        constraintSet.connect(
            imgLeftBottom.id,
            ConstraintSet.BOTTOM,
            frmBorderText.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imgLeftBottom.id,
            ConstraintSet.START,
            frmBorderText.id,
            ConstraintSet.START
        )

        //Constraints for imgRotate
        constraintSet.connect(
            imgRotate.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imgRotate.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            imgRotate.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )

        // Apply constraints
        constraintSet.applyTo(this)

        // Add listeners
        imgPhotoEditorClose.setOnClickListener { showToast("Clicked Close") }
        imgRightTopText.setOnClickListener { showToast("Clicked Right Top") }
        imgRightBottom.setOnClickListener { showToast("Clicked Right Bottom") }
        imgLeftBottom.setOnClickListener { showToast("Clicked Left Bottom") }
        imgRotate.setOnClickListener { showToast("Clicked Rotate btn") }

    }

    private fun createFrameLayout(): ConstraintLayout {
        val frameLayout = ConstraintLayout(context)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )
        frameLayout.id = View.generateViewId()
        frameLayout.layoutParams = layoutParams
        // Set constraints to center the TextView
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)

        constraintSet.connect(
            frameLayout.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            frameLayout.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            frameLayout.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            frameLayout.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.applyTo(this)
        return frameLayout
    }

    private fun createTextView(): TextView {
        val textView = TextView(context)
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )

        layoutParams.gravity = Gravity.CENTER

        textView.id = View.generateViewId()
        textView.layoutParams = layoutParams
        textView.text = "dummy"
        textView.setBackgroundResource(R.drawable.rounded_border_tv)

        return textView
    }


    private fun createImageView(imageResource: Int): ImageView {
        val imageView = ImageView(context)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        imageView.id = View.generateViewId()
        imageView.layoutParams = layoutParams
        imageView.setImageResource(imageResource)
        return imageView
    }

    private fun showToast(message: String) {
        log(message)
    }
}
