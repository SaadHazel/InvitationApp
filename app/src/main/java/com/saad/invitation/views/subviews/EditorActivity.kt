package com.saad.invitation.views.subviews

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.saad.invitation.R
import com.saad.invitation.databinding.ActivityEditorBinding
import com.saad.invitation.learning.BackgroundListener
import com.saad.invitation.learning.CornerListener
import com.saad.invitation.learning.CustomConstraintLayout
import com.saad.invitation.learning.FrameLayoutFactory

class EditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorBinding
    private lateinit var backgroundListener: BackgroundListener
    private lateinit var cornerListener: CornerListener

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor)
        // Create the main FrameLayout

        backgroundListener = BackgroundListener { }
        val customConstraintLayout = CustomConstraintLayout(this)
        binding.mainLayout.addView(customConstraintLayout)
        customConstraintLayout.setOnTouchListener(backgroundListener)


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