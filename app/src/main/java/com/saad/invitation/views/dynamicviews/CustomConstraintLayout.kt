package com.saad.invitation.views.dynamicviews

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.saad.invitation.R
import com.saad.invitation.learning.BackgroundListener
import com.saad.invitation.utils.log

@SuppressLint("ViewConstructor")
class CustomConstraintLayout(context: Context, private val viewGroup: ConstraintLayout) :
    ConstraintLayout(context) {
    private lateinit var backgroundListener: BackgroundListener

    init {
        createLayout()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun createLayout() {

        val frmBorderText = createFrameLayout()
        backgroundListener = BackgroundListener { }
        this.setOnTouchListener(backgroundListener)

        val imgPhotoEditorClose = createImageView(R.drawable.dot)
        val imgRightTopText = createImageView(R.drawable.dot)
        val imgRightBottom = createImageView(R.drawable.dot)
        val imgLeftBottom = createImageView(R.drawable.dot)
        val imgRotate = createImageView(R.drawable.round_rotate_90_degrees_ccw_24)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val paddingSize = resources.getDimensionPixelSize(R.dimen.frame_layout_padding)

        this.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)

        this.setBackgroundColor(Color.BLUE)
        this.layoutParams = layoutParams
        // Add views to the ConstraintLayout
        addView(frmBorderText)

        viewGroup.addView(imgRotate)
//        addView(imgRotate)
//        frmBorderText.addView(tvPhotoEditorText)
        addView(imgPhotoEditorClose)
        addView(imgRightTopText)
        addView(imgRightBottom)
        addView(imgLeftBottom)

        val layoutParamsImageRotate = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val marginSize = resources.getDimensionPixelSize(R.dimen.rotate_img_margin)
        layoutParamsImageRotate.setMargins(marginSize, 30, marginSize, marginSize)

        imgRotate.layoutParams = layoutParamsImageRotate
        clickListener(frmBorderText, "Clicked: FrameLayout")
//        clickListener(tvPhotoEditorText, "Clicked: TextView")
        clickListener(this, "Clicked: Constraint Layout")
        frmBorderText.setBackgroundColor(Color.MAGENTA)
        // Create constraints for parent
        val constraintSetForParent = ConstraintSet()
        constraintSetForParent.clone(viewGroup)

        //Constraint for custom view
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
        /*     constraintSet.connect(
                 frmBorderText.id,
                 ConstraintSet.BOTTOM,
                 ConstraintSet.PARENT_ID,
                 ConstraintSet.BOTTOM
             )*/

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
        /*  constraintSet.connect(
              imgRotate.id,
              ConstraintSet.BOTTOM,
              ConstraintSet.PARENT_ID,
              ConstraintSet.BOTTOM
          )*/
        constraintSetForParent.connect(
            imgRotate.id,
            ConstraintSet.START,
            this.id,
            ConstraintSet.START
        )
        constraintSetForParent.connect(
            imgRotate.id,
            ConstraintSet.END,
            this.id,
            ConstraintSet.END
        )
        constraintSetForParent.connect(
            imgRotate.id,
            ConstraintSet.TOP,
            this.id,
            ConstraintSet.BOTTOM
        )

        // Apply constraints
        constraintSetForParent.applyTo(
            viewGroup
        )
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
        val paddingSize = resources.getDimensionPixelSize(R.dimen.frame_layout_padding)

        frameLayout.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
        val layoutParams = ConstraintLayout.LayoutParams(
//            200,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        frameLayout.id = View.generateViewId()
        frameLayout.layoutParams = layoutParams

        val tvPhotoEditorText = createTextView()
        frameLayout.addView(tvPhotoEditorText)


        val constraintSet = ConstraintSet()
        constraintSet.clone(frameLayout)

        constraintSet.center(
            tvPhotoEditorText.id,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0,
            0.5f
        )
        constraintSet.center(
            tvPhotoEditorText.id,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0,
            0.5f
        )
        constraintSet.applyTo(frameLayout)

        return frameLayout
    }

    private fun createTextView(): TextView {
        val textView = TextView(context)
        val paddingSize = resources.getDimensionPixelSize(R.dimen.text_view_padding)

        textView.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)

        val textViewLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        textView.id = View.generateViewId()
        textView.layoutParams = textViewLayoutParams
        textView.text = "dummygjgghjhgjghjgjgjghg"
        textView.setBackgroundResource(R.drawable.rounded_border_tv)
//        textView.setBackgroundColor(Color.LTGRAY)
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

    private fun clickListener(view: View, message: String) {
        view.setOnClickListener {
            log(message)
        }
    }


}
