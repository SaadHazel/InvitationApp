package com.saad.invitation.views

import android.annotation.SuppressLint
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
import com.saad.invitation.listners.CornerIconListener
import com.saad.invitation.listners.DraggableImageView
import com.saad.invitation.listners.DraggableTextView
import com.saad.invitation.utils.log
import com.saad.invitation.views.dynamicviews.FrameLayoutFactory

class EditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditorBinding
    private lateinit var backgroundListener: BackgroundListener
    private lateinit var cornerIconListener: CornerIconListener

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_editor)
        var lastTextViewX = 0f
        var lastTextViewY = 0f
        val mConstraintLayout = binding.mainLayout
        val paddingSize = resources.getDimensionPixelSize(R.dimen.constraint_layout_padding)
        mConstraintLayout.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)
        var imgEndTop = createCornerImageView(R.drawable.dot)
//        val textView = createTextView()
        val draggableTextView = DraggableTextView(this)
        val draggableImageView = DraggableImageView(this)
        mConstraintLayout.addView(draggableTextView)
        mConstraintLayout.addView(draggableImageView)

        // Enable drag and drop for DraggableImageView
        // Enable drag and drop for DraggableTextView

        // Create CornerIcon for top-left corner
        val iconSize = 40 // Set your icon size
        val bottomRightIcon = CornerIconListener(this, iconSize, R.drawable.dot)
        val topRightIcon = CornerIconListener(this, iconSize, R.drawable.dot)
        val topLeftIcon = CornerIconListener(this, iconSize, R.drawable.dot)
        val bottomLeftIcon = CornerIconListener(this, iconSize, R.drawable.dot)
        val bottomCenter =
            CornerIconListener(this, 150, R.drawable.baseline_minimize_24)

        val bottomRightIconImage = CornerIconListener(this, iconSize, R.drawable.dot)
        val topRightIconImage = CornerIconListener(this, iconSize, R.drawable.dot)
        val topLeftIconImage = CornerIconListener(this, iconSize, R.drawable.dot)
        val bottomLeftIconImage = CornerIconListener(this, iconSize, R.drawable.dot)
        val bottomCenterImage =
            CornerIconListener(this, 150, R.drawable.baseline_minimize_24)

        //Adding views to Layout
        mConstraintLayout.addView(bottomRightIcon)
        mConstraintLayout.addView(topRightIcon)
        mConstraintLayout.addView(topLeftIcon)
        mConstraintLayout.addView(bottomLeftIcon)

        mConstraintLayout.addView(bottomCenter)

        mConstraintLayout.addView(bottomRightIconImage)
        mConstraintLayout.addView(topRightIconImage)
        mConstraintLayout.addView(topLeftIconImage)
        mConstraintLayout.addView(bottomLeftIconImage)

        mConstraintLayout.addView(bottomCenterImage)
        draggableImageView.enableDragAndDrop(
            topLeftIconImage,
            topRightIconImage,
            bottomRightIconImage,
            bottomLeftIconImage,
            bottomCenterImage
        )

        draggableTextView.enableDragAndDrop(
            topLeftIcon,
            topRightIcon,
            bottomRightIcon,
            bottomLeftIcon,
            bottomCenter
        )

//        draggableTextView.attachTo(draggableTextView, topLeftIcon)

        // Attaching the icon to the DraggableTextView
        bottomRightIcon.attachTo(draggableTextView, CornerIconListener.Corner.BOTTOM_RIGHT)
        topRightIcon.attachTo(draggableTextView, CornerIconListener.Corner.TOP_RIGHT)
        topLeftIcon.attachTo(draggableTextView, CornerIconListener.Corner.TOP_LEFT)
        bottomLeftIcon.attachTo(draggableTextView, CornerIconListener.Corner.BOTTOM_LEFT)
        bottomCenter.attachTo(draggableTextView, CornerIconListener.Corner.BOTTOM_CENTER)
        // Attaching the icon to the DraggableImageView
        bottomRightIconImage.attachTo(draggableImageView, CornerIconListener.Corner.BOTTOM_RIGHT)
        topRightIconImage.attachTo(draggableImageView, CornerIconListener.Corner.TOP_RIGHT)
        topLeftIconImage.attachTo(draggableImageView, CornerIconListener.Corner.TOP_LEFT)
        bottomLeftIconImage.attachTo(draggableImageView, CornerIconListener.Corner.BOTTOM_LEFT)
        bottomCenterImage.attachTo(draggableImageView, CornerIconListener.Corner.BOTTOM_CENTER)
        // Enabling resizing on touch Icons
        bottomRightIcon.enableResizeOnTouch(
            draggableTextView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600,
        )
        topRightIcon.enableResizeOnTouch(
            draggableTextView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )
        topLeftIcon.enableResizeOnTouch(
            draggableTextView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )
        bottomLeftIcon.enableResizeOnTouch(
            draggableTextView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )
        bottomCenter.enableResizeOnTouch(
            draggableTextView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )

        // Enabling resizing on touch Icons
        bottomRightIconImage.enableResizeOnTouch(
            draggableImageView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600,
        )
        topRightIconImage.enableResizeOnTouch(
            draggableImageView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )
        topLeftIconImage.enableResizeOnTouch(
            draggableImageView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )
        bottomLeftIconImage.enableResizeOnTouch(
            draggableImageView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )
        bottomCenterImage.enableResizeOnTouch(
            draggableImageView,
            minWidth = 100,
            minHeight = 100,
            maxWidth = 600,
            maxHeight = 600
        )


        /*   cornerIconListener = CornerIconListener(textView)
           mConstraintLayout.addView(textView)

           backgroundListener = BackgroundListener { showCorner ->
               val constraintSetImgEndTop = ConstraintSet()
               if (showCorner) {

                   // Checking  if imgEndTop already has a parent
                   val parentView = imgEndTop.parent as? ViewGroup
                   parentView?.removeView(imgEndTop)


                   imgEndTop = createCornerImageView(R.drawable.dot)
                   mConstraintLayout.addView(imgEndTop)
                   imgEndTop.setOnTouchListener(cornerIconListener)
   //                log("Before applying constraints - imgEndTop position: (x=${imgEndTop.x}, y=${imgEndTop.y})")

                   constraintSetImgEndTop.clone(mConstraintLayout)
                   //Top End Corner Image
                   constraintSetImgEndTop.connect(
                       imgEndTop.id,
                       ConstraintSet.TOP,
                       textView.id,
                       ConstraintSet.TOP
                   )
                   constraintSetImgEndTop.connect(
                       imgEndTop.id,
                       ConstraintSet.END,
                       textView.id,
                       ConstraintSet.END
                   )
                   */
        /*     constraintSetImgEndTop.connect(
                         imgEndTop.id,
                         ConstraintSet.BOTTOM,
                         textView.id,
                         ConstraintSet.TOP
                     )
                     constraintSetImgEndTop.connect(
                         imgEndTop.id,
                         ConstraintSet.START,
                         textView.id,
                         ConstraintSet.END
                     )*/
        /*

                constraintSetImgEndTop.setHorizontalBias(imgEndTop.id, 0.5f)
                constraintSetImgEndTop.setVerticalBias(imgEndTop.id, 0.5f)

                // Set the image view's dimensions (adjust these as needed)
                constraintSetImgEndTop.constrainWidth(imgEndTop.id, ConstraintSet.WRAP_CONTENT)
                constraintSetImgEndTop.constrainHeight(imgEndTop.id, ConstraintSet.WRAP_CONTENT)

//                log("Before applying constraints: $constraintSetImgEndTop")
                constraintSetImgEndTop.applyTo(mConstraintLayout)
//                log("After applying constraints: $constraintSetImgEndTop")

//                log("After applying constraints - imgEndTop position: (x=${imgEndTop.x}, y=${imgEndTop.y})")

            } else {
                // Store the last position of textView when showCorner is false
                lastTextViewX = textView.x
                lastTextViewY = textView.y
                // Remove constraints for imgEndTop
                constraintSetImgEndTop.clear(imgEndTop.id, ConstraintSet.TOP)
                constraintSetImgEndTop.clear(imgEndTop.id, ConstraintSet.END)
                constraintSetImgEndTop.clear(imgEndTop.id, ConstraintSet.BOTTOM)
                constraintSetImgEndTop.clear(imgEndTop.id, ConstraintSet.START)
                // Checking if imgEndTop is not null and has a parent, then remove it
                val parentView = imgEndTop.parent as? ViewGroup
                parentView?.removeView(imgEndTop)
                log("imgEndTopElsePart: ${imgEndTop.id}")
            }

            val decimalPlaces = 2

            // Round the values
            val roundedLastTextViewX =
                round(lastTextViewX * 10.0.pow(decimalPlaces)) / 10.0.pow(decimalPlaces)
            val roundedLastTextViewY =
                round(lastTextViewY * 10.0.pow(decimalPlaces)) / 10.0.pow(decimalPlaces)

            // Rounded Values
            imgEndTop.x = roundedLastTextViewX.toFloat()
            imgEndTop.y = roundedLastTextViewY.toFloat()


            log("imgEndTop.x:  ${imgEndTop.x} = lastTextViewX: $lastTextViewX :: RoundedLastTextViewX = $roundedLastTextViewX")
            log("imgEndTop.y:  ${imgEndTop.y} = lastTextViewY: $lastTextViewY :: RoundedLastTextViewY = $roundedLastTextViewY")


        }

        textView.setOnTouchListener(backgroundListener)*/

        //Text view constraints
        /* constraintSetTextView.connect(
             textView.id,
             ConstraintSet.TOP,
             ConstraintSet.PARENT_ID,
             ConstraintSet.TOP
         )
         constraintSetTextView.connect(
             textView.id,
             ConstraintSet.END,
             ConstraintSet.PARENT_ID,
             ConstraintSet.END
         )
         constraintSetTextView.connect(
             textView.id,
             ConstraintSet.BOTTOM,
             ConstraintSet.PARENT_ID,
             ConstraintSet.BOTTOM
         )
         constraintSetTextView.connect(
             textView.id,
             ConstraintSet.START,
             ConstraintSet.PARENT_ID,
             ConstraintSet.START
         )
         log("Before applying constraints TextView: $constraintSetTextView")
         constraintSetTextView.applyTo(mConstraintLayout)
         log("After applying constraints TextView: $constraintSetTextView")*/

        // Create ConstraintSet for imgEndTop

        // Create the main FrameLayout
        /*        val customConstraintLayout = CustomConstraintLayout(this, binding.mainLayout)
                binding.mainLayout.addView(customConstraintLayout)

                val customConstraint = createConstraintLayout()
                        binding.mainLayout.addView(customConstraint)
                customConstraint.setOnTouchListener(backgroundListener)  */


        /*val imgStartTop = createCornerImageView(R.drawable.dot)
        val imgEndBottom = createCornerImageView(R.drawable.dot)
        val imgStartBottom = createCornerImageView(R.drawable.dot)
        val imgRotateBottom =
            createCornerImageView(R.drawable.round_rotate_90_degrees_ccw_24)*/


        /*mConstraintLayout.addView(imgStartTop)
        mConstraintLayout.addView(imgEndBottom)
        mConstraintLayout.addView(imgStartBottom)
        mConstraintLayout.addView(imgRotateBottom)*/


        //Top Start Corner Image
        /*    constraintSet.connect(
                imgStartTop.id,
                ConstraintSet.TOP,
                textView.id,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                imgStartTop.id,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START
            )*/


        //Bottom End Corner Image
        /*    constraintSet.connect(
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
            )*/

        //Start Bottom Corner image
        /*        constraintSet.connect(
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
                )*/

        //Bottom Rotate Img
        /* constraintSetRotate.connect(
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
         constraintSetRotate.applyTo(binding.mainLayout)*/

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

    @SuppressLint("SetTextI18n")
    private fun createTextView(): TextView {
        val textView = TextView(this)
        val paddingSize = resources.getDimensionPixelSize(R.dimen.text_view_padding)

        textView.setPadding(paddingSize, paddingSize, paddingSize, paddingSize)

        val textViewLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        textView.id = View.generateViewId()
        log("TextViewID: ${textView.id}")
        textView.layoutParams = textViewLayoutParams
        textView.text = "LongDummyText"
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setBackgroundResource(R.drawable.rounded_border_tv)
        return textView
    }

    private fun createCornerImageView(imageResource: Int): ImageView {
        val imageView = ImageView(this)
        val layoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        imageView.id = View.generateViewId()
        log("CornerDotIDs: ${imageView.id}")
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
        imageView.setOnClickListener {
            showToast("Clicked $tag")
        }

        parent.addView(imageView)
    }

    private fun showToast(message: String) {
        Toast.makeText(this@EditorActivity, message, Toast.LENGTH_SHORT).show()
    }

}