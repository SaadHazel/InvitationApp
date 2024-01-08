package com.saad.invitation.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.saad.invitation.R
import com.saad.invitation.databinding.ActivityEditorBinding
import com.saad.invitation.learning.BackgroundListener
import com.saad.invitation.learning.CornerListener
import com.saad.invitation.views.dynamicviews.FrameLayoutFactory

class EditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorBinding
    private lateinit var cornerListener: CornerListener
    private lateinit var backgroundListener: BackgroundListener

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor)
        backgroundListener = BackgroundListener {}
        // Create the main FrameLayout
//        val customConstraintLayout = CustomConstraintLayout(this, binding.mainLayout)
//        binding.mainLayout.addView(customConstraintLayout)

        val customConstraint = createConstraintLayout()
        val paddingSize = resources.getDimensionPixelSize(R.dimen.constraint_layout_padding)


        binding.mainLayout.addView(customConstraint)
        customConstraint.setOnTouchListener(backgroundListener)
        val imgStartTop = createCornerImageView(R.drawable.dot, customConstraint)
        val imgEndTop = createCornerImageView(R.drawable.dot, customConstraint)
        val imgEndBottom = createCornerImageView(R.drawable.dot, customConstraint)
        val imgStartBottom = createCornerImageView(R.drawable.dot, customConstraint)
        val imgRotateBottom =
            createCornerImageView(R.drawable.round_rotate_90_degrees_ccw_24, customConstraint)

        binding.mainLayout.addView(imgRotateBottom)

        customConstraint.addView(imgStartTop)
        customConstraint.addView(imgEndTop)
        customConstraint.addView(imgEndBottom)
        customConstraint.addView(imgStartBottom)
        customConstraint.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
        customConstraint.setBackgroundColor(Color.MAGENTA)

        val constraintSet = ConstraintSet()
        constraintSet.clone(customConstraint)
        val constraintSetRotate = ConstraintSet()
        constraintSetRotate.clone(binding.mainLayout)

        //Top Start Corner Image
        constraintSet.connect(
            imgStartTop.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            imgStartTop.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )

        //Top End Corner Image
        constraintSet.connect(
            imgEndTop.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            imgEndTop.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )

        //Bottom End Corner Image
        constraintSet.connect(
            imgEndBottom.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imgEndBottom.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )

        //Start Bottom Corner image
        constraintSet.connect(
            imgStartBottom.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            imgStartBottom.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )

        //Bottom Rotate Img
        constraintSetRotate.connect(
            imgRotateBottom.id,
            ConstraintSet.START,
            customConstraint.id,
            ConstraintSet.START
        )
        constraintSetRotate.connect(
            imgRotateBottom.id,
            ConstraintSet.END,
            customConstraint.id,
            ConstraintSet.END
        )
        constraintSetRotate.connect(
            imgRotateBottom.id,
            ConstraintSet.TOP,
            customConstraint.id,
            ConstraintSet.BOTTOM
        )

        constraintSet.applyTo(customConstraint)
        constraintSetRotate.applyTo(binding.mainLayout)
        /*    val frameLayout = FrameLayoutFactory.createMainFrameLayout(this)
            binding.mainLayout.addView(frameLayout)
    
            backgroundListener = BackgroundListener { }
    
            frameLayout.setOnTouchListener(backgroundListener)
            frameLayout.setBackgroundColor(Color.BLUE)
    
            // Create the border FrameLayout
            val borderFrameLayout = FrameLayoutFactory.createBorderFrameLayout(this)
            frameLayout.addView(borderFrameLayout)
    //        borderFrameLayout.setBackgroundResource(R.drawable.rounded_border_tv)
    
            // Find views by their IDs
            val textView = FrameLayoutFactory.createTextView(this)
    
            // Customize the TextView
            textView.text = "dummy"
            textView.setTextColor(resources.getColor(android.R.color.black)) // You can use your color
            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
            )
            textView.setBackgroundResource(R.drawable.rounded_border_tv)
            layoutParams.gravity = Gravity.CENTER
            textView.layoutParams = layoutParams
            // Add the TextView to the border FrameLayout
            borderFrameLayout.addView(textView)
    
            cornerListener = CornerListener(frameLayout)
    
            // Add the the ImageViews with click listeners
            addImageView(
                borderFrameLayout,
                R.drawable.baseline_circle_24,
                38,
                38,
                "top|start",
                "Top-Left"
            )
            addImageView(
                borderFrameLayout,
                R.drawable.baseline_circle_24,
                38,
                38,
                "top|end",
                "Top-Right"
            )
            addImageView(
                borderFrameLayout,
                R.drawable.baseline_circle_24,
                38,
                38,
                "bottom|end",
                "Bottom-Right"
            )
            addImageView(
                borderFrameLayout,
                R.drawable.baseline_circle_24,
                38,
                38,
                "bottom|start",
                "Bottom-Left"
            )
            addImageView(
                frameLayout,
                R.drawable.round_rotate_90_degrees_ccw_24,
                48,
                48,
                "bottom|center",
                "Bottom-center"
            )*/
    }

    private fun createConstraintLayout(): ConstraintLayout {
        val constraintLayout = ConstraintLayout(this)
        val paddingSize = resources.getDimensionPixelSize(R.dimen.frame_layout_padding)

        constraintLayout.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
        val layoutParams = ConstraintLayout.LayoutParams(
//            200,
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        constraintLayout.id = View.generateViewId()
        constraintLayout.layoutParams = layoutParams

        val textView = createTextView()
        constraintLayout.addView(textView)


        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        constraintSet.center(
            textView.id,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            0,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END,
            0,
            0.5f
        )
        constraintSet.center(
            textView.id,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0,
            0.5f
        )
        constraintSet.applyTo(constraintLayout)
        return constraintLayout
    }

    private fun createTextView(): TextView {
        val textView = TextView(this)
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

    private fun createCornerImageView(imageResource: Int, parent: ConstraintLayout): ImageView {
        val imageView = ImageView(this)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        imageView.id = View.generateViewId()
        imageView.layoutParams = layoutParams
        imageView.setImageResource(imageResource)
        return imageView
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addImageView(
        parent: FrameLayout,
        imageResource: Int,
        width: Int,
        height: Int,
        gravity: String,
        tag: String,
    ) {
        /*  val imageView = FrameLayoutFactory.createImageView(this, imageResource, width)
          imageView.layoutParams = FrameLayout.LayoutParams(width, height).apply {
              this.gravity = FrameLayoutFactory.getGravityFromString(gravity)

          }*/
        val imageView = FrameLayoutFactory.createImageView(this, imageResource, width)

        // Set layout parameters
        val layoutParams = FrameLayout.LayoutParams(width, height)
        layoutParams.gravity = FrameLayoutFactory.getGravityFromString(gravity)

        // Check if gravity is bottom|center and add margins
        if (gravity == "bottom|center") {
            layoutParams.bottomMargin = 40 // Set your desired bottom margin value
        }

        imageView.layoutParams = layoutParams

        imageView.setOnTouchListener(cornerListener)
        imageView.setOnClickListener {
            showToast("Clicked $tag")
        }

        parent.addView(imageView)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@EditorActivity, message, Toast.LENGTH_SHORT).show()
    }


}