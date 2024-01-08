package com.saad.invitation.views


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.saad.invitation.R
import com.saad.invitation.databinding.ActivityDragBinding
import com.saad.invitation.databinding.ViewTextEditorBinding
import com.saad.invitation.learning.ResizableView
import com.saad.invitation.learning.SingleTouchListner
import com.saad.invitation.learning.UpdatedTouchListner
import com.saad.invitation.learning.debug_tag
import com.saad.invitation.learning.isViewBeingDragged
import com.saad.invitation.utils.createBitmapDrawableFromView
import com.saad.invitation.utils.generateBitmapFromView
import com.saad.invitation.utils.log
import com.saad.invitation.views.subviews.MyBottomSheetDialogFragment


const val DEBUG_TAG = "Gestures"

class DragActivity : AppCompatActivity() {
    private var clickedFlag: Boolean = false
    private lateinit var imageviewDot: ImageView
    private lateinit var textLayout: View
    private lateinit var relativeLayout: RelativeLayout
    private var updateTouchListener: UpdatedTouchListner? = null
    private lateinit var binding: ActivityDragBinding
    private lateinit var secondBinding: ViewTextEditorBinding
    private lateinit var currentText: String

    //    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var cardView: CardView

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drag)
        cardView = binding.cardView
        relativeLayout = createRelativeLayout(this)
        relativeLayout.setBackgroundResource(R.drawable.rounded_border_tv)
        relativeLayout.setPadding(32, 32, 32, 32)

        val dotSize = 50 // Adjust the size of the dots as needed

        // Bottom-right dot
        val bottomRightDot = createDotView(this, dotSize)
        val paramsBottomRight = RelativeLayout.LayoutParams(dotSize, dotSize)
        paramsBottomRight.addRule(RelativeLayout.ALIGN_PARENT_END)
        paramsBottomRight.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        relativeLayout.addView(bottomRightDot, paramsBottomRight)
        setClickListener(bottomRightDot, "Bottom-Right")


        binding.btnImage.setOnClickListener {
            log("Clicked on text")
        }
        binding.btnImage.setBackgroundResource(R.drawable.rounded_border_tv)

        val myIcon = ContextCompat.getDrawable(this, R.drawable.baseline_circle_24)
        binding.btnImage.setCompoundDrawablesWithIntrinsicBounds(null, myIcon, null, null)
        binding.btnImage.compoundDrawablePadding = (18f).dpToPx(this)


        binding.btnImage.gravity = Gravity.END or Gravity.TOP

//        binding.btnImage.setBackgroundResource(R.drawable.tryicons)
        /*   val inflaters = LayoutInflater.from(binding.btnImage.context)
           val backgroundLayout = inflaters.inflate(R.layout.view_text_editor, null)
           binding.btnImage.background = createBitmapDrawableFromView(backgroundLayout)
           val topbutton = backgroundLayout.findViewById<ImageView>(R.id.img_right_top_text)
           log("TopButton: $topbutton")
           topbutton.setOnClickListener {
               log("Click on background")
           }*/
        Log.d("Selected Text", "oncreate clickedFlag: " + clickedFlag)



        relativeLayout.setOnClickListener {

            Log.d("Selected Text", "imagedot: ")
        }


        updateTouchListener = UpdatedTouchListner { selectedText ->
            selectedText as TextView
            log("Selected Text: DragActivity")


//            setTextViewBackground(selectedText)

            /*  secondBinding = ViewTextEditorBinding.inflate(layoutInflater)
              val textViewInLayout = secondBinding.tvPhotoEditorText
              textViewInLayout.text = selectedText.text
              val finalSize = selectedText.textSize - textViewInLayout.textSize

              textViewInLayout.textSize = finalSize
              textViewInLayout.visibility = View.INVISIBLE*/

//            selectedText.setBackgroundResource(R.drawable.rounded_border_tv)
            /* secondBinding.imgRightTop.setOnClickListener {
                 log("This is log for clicking")
             }*/
        }
//        mDetector = GestureDetectorCompat(this, SingleTouchListner(null, null))

        Log.d(debug_tag, "$isViewBeingDragged")
        val cardView = binding.cardView
        val viewImage = binding.image
        val viewText = binding.etText
        val viewTextTwo = binding.etText2

        val message = intent.getStringExtra("message_key")
        val imageString =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQt74tgdFC3TA4w02EtPvNvwlPzBx-pOaE_X910ct5Jzg&s"

        Glide.with(this)
            .load(imageString)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(viewImage)

        viewImage.setOnTouchListener(SingleTouchListner())

        Glide.with(this)
            .asBitmap()
            .load(message)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Set the loaded bitmap as the background of the CardView
                    val drawable = BitmapDrawable(resources, resource)
                    cardView.background = drawable
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Implement if needed
                }
            })

        viewText.visibility = View.GONE
        viewTextTwo.visibility = View.GONE

        val inflater = layoutInflater
        val secondLayout: View = inflater.inflate(R.layout.view_text_editor, null)
        val textViewFromSecondLayout = secondLayout.findViewById<ImageView>(R.id.img_right_top_text)

        binding.btnGotoEditor.setOnClickListener {
            val intent = Intent(this, EditorActivity::class.java)
            startActivity(intent)
        }
        textViewFromSecondLayout.setOnClickListener {
            Toast.makeText(this, "Click on the edge", Toast.LENGTH_SHORT).show()
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    Log.d(debug_tag, "Item 1")
                    showAddTextDialog()
                    true
                }

                R.id.item_2 -> {
                    openGallery()
                    true
                }

                R.id.item_3 -> {

                    val myCardView: CardView = binding.cardView
                    val bitmap = generateBitmapFromView(myCardView)
                    val bottomSheetFragment = MyBottomSheetDialogFragment(bitmap, this)
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)



                    true
                }

                R.id.item_4 -> {
                    if (SingleTouchListner.currentViewForDeletion != null) {
                        SingleTouchListner.currentViewForDeletion!!.visibility = View.GONE
                        Toast.makeText(this, "Element deleted", Toast.LENGTH_SHORT).show()
                    }
                    true
                }

                else -> false
            }
        }


        /*     val deleteView: View = viewImage // your delete view reference
             val photoEditImageView: ImageView? = null // your photo edit image view reference
             val onPhotoEditorListener: OnPhotoEditorListener? = null// your listener implementation
             val viewState: PhotoEditorViewState = PhotoEditorViewState()// your view state

             val multiTouchListener = MultiTouchListener(
                 context = this, // or applicationContext if needed
                 deleteView = deleteView,
                 photoEditorView = viewImage,
                 photoEditImageView = photoEditImageView,
                 mIsPinchScalable = true, // or false based on your requirement
                 onPhotoEditorListener = onPhotoEditorListener,
                 viewState = viewState
             )

             viewImage.setOnTouchListener(multiTouchListener)
     */
        /*   viewImage.source.setImageResource(R.drawable.image2)

           val mTextRobotoTf = ResourcesCompat.getFont(this, com.saad.invitation.R.font.roboto_medium)*/

//loading font from asset

//loading font from asset
//        val mEmojiTypeFace = Typeface.createFromAsset(assets, "emojione-android.ttf")

        /*     mPhotoEditor = PhotoEditor.Builder(this, viewImage)
                 .setPinchTextScalable(true)
                 .setClipSourceImage(true)
                 .setDefaultTextTypeface(mTextRobotoTf)
                 .build()

             mPhotoEditor.addText("inputText", Color.WHITE);*/

        /*       val view = binding.image
       ////        view.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_background, null))

       //        binding.etText.setOnTouchListener(MultiTouchTextListener())

       //        view.setOnTouchListener(ImageTouchListner())
               view.setOnTouchListener(MultiTouchImageListener())
               binding.etText.setOnTouchListener(TextTouchListener())*/

//        binding.image.setOnTouchListener(MultiTouchImageListener())

//        mDetector = GestureDetectorCompat(this, MyGestureListener())

        /*        binding.image.setOnTouchListener { _, event ->
        //            mDetector.onTouchEvent(event)
                    onTouchEvent(event)
                    true
                }*/
    }

    private fun createRelativeLayout(context: Context): RelativeLayout {
        val relativeLayout = RelativeLayout(context)
        relativeLayout.layoutParams = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.WRAP_CONTENT,
            400,
            400
        )
        return relativeLayout
    }

    private fun createDotView(context: Context, dotSize: Int): ImageView {
        val dotView = ImageView(context)
        dotView.setImageResource(R.drawable.dot) // You can use a dot image or customize as needed
        dotView.layoutParams = RelativeLayout.LayoutParams(dotSize, dotSize)
        return dotView
    }

    private fun setClickListener(view: View, message: String) {
        view.setOnClickListener {
            Toast.makeText(this@DragActivity, "Clicked $message", Toast.LENGTH_SHORT).show()
        }
    }

    fun Float.dpToPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            context.resources.displayMetrics
        ).toInt()
    }

    @SuppressLint("ClickableViewAccessibility")
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            val selectedImageUri: Uri? = data?.data

            val newImageView = ImageView(this)
            newImageView.id = View.generateViewId()

            Glide.with(this)
                .load(selectedImageUri)
                .centerInside()
                .into(newImageView)

            val layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            layoutParams.width = 300
            layoutParams.height = 300
            newImageView.layoutParams = layoutParams
            newImageView.scaleType = ImageView.ScaleType.MATRIX
            newImageView.setOnTouchListener(updateTouchListener)

            cardView.addView(newImageView)

        }
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun showAddTextDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_text, null)
        val editText = dialogView.findViewById<EditText>(R.id.editText)
        val btnOk = dialogView.findViewById<Button>(R.id.btnOk)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        editText.requestFocus()

        //make extension function
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        btnOk.setOnClickListener {
            val text = editText.text.toString().trim()
            if (text.isNotEmpty()) {
                // Call a method to create and add a new TextView with the entered text
//                addResizableTextView(text)
                addNewTextView(text)
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addNewTextView(text: String) {
        val newTextView = TextView(this)
        newTextView.text = text
        newTextView.textSize = 26f
        newTextView.gravity = Gravity.CENTER
        newTextView.setTextColor(Color.BLACK)
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        newTextView.layoutParams = layoutParams

        newTextView.setOnTouchListener(updateTouchListener)


        val container = binding.cardView
//        container.addView(newTextView)
        container.addView(relativeLayout)
        newTextView.setPadding(20)
        newTextView.setBackgroundColor(Color.GRAY)
        relativeLayout.addView(newTextView)

    }

    private fun setTextViewBackground(textView: TextView) {

        val inflater = LayoutInflater.from(textView.context)
        textLayout = inflater.inflate(R.layout.view_text_editor, null)
        val textViewInLayout = textLayout.findViewById<TextView>(R.id.tvPhotoEditorText)
        imageviewDot = textLayout.findViewById<ImageView>(R.id.img_right_top_text)
        textViewInLayout.text = textView.text
        textViewInLayout.setTextColor(Color.BLUE)
        val finalSize = textView.textSize - textViewInLayout.textSize
        textViewInLayout.textSize = finalSize

        textViewInLayout.visibility = View.INVISIBLE
        textView.background = createBitmapDrawableFromView(textLayout)
        clickedFlag = true
        Log.d("Selected Text", "setTextViewBackground clickedFlag: " + clickedFlag)

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addResizableTextView(text: String) {
        val container = ResizableView(this)

        // Customize the inner TextView
        val innerTextView = TextView(this)
        innerTextView.text = text
        innerTextView.textSize = 26f
        innerTextView.gravity = Gravity.CENTER
        innerTextView.setTextColor(Color.BLACK)

        // Add the inner TextView to the ResizableView
        container.addView(innerTextView)

        // Set layout parameters for the ResizableView
        val layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        container.layoutParams = layoutParams

        // Add the ResizableView to your container
        binding.cardView.addView(container)

        // Set touch listener for both ResizableView and inner TextView
        container.setOnTouchListener { _, event ->
            handleTouchEvent(container, event)
        }

        innerTextView.setOnTouchListener { _, event ->
            handleTouchEvent(container, event)
        }
    }

    private fun handleTouchEvent(container: ResizableView, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                container.startX = event.rawX - container.translationX
                container.startY = event.rawY - container.translationY
                container.originalWidth = container.width.toFloat()
                container.originalHeight = container.height.toFloat()
                container.selectedEdge =
                    container.getSelectedEdge(container.startX, container.startY)
            }

            MotionEvent.ACTION_MOVE -> {
                if (container.selectedEdge != null) {
                    // Resize the container based on the drag
                    val offsetX = event.rawX - container.startX
                    val offsetY = event.rawY - container.startY

                    val newWidth = calculateNewDimension(
                        container.originalWidth,
                        offsetX,
                        container.MIN_SIZE.toFloat(),
                        container.MAX_SIZE.toFloat()
                    )
                    val newHeight = calculateNewDimension(
                        container.originalHeight,
                        offsetY,
                        container.MIN_SIZE.toFloat(),
                        container.MAX_SIZE.toFloat()
                    )

                    val layoutParams = container.layoutParams
                    layoutParams.width = newWidth.toInt()
                    layoutParams.height = newHeight.toInt()
                    container.layoutParams = layoutParams

                    container.invalidate()
                } else {
                    // Move the container based on the drag
                    container.translationX = event.rawX - container.startX
                    container.translationY = event.rawY - container.startY
                }
            }

            MotionEvent.ACTION_UP -> {
                container.selectedEdge = null
            }
        }
        return true
    }

    private fun calculateNewDimension(
        originalDimension: Float,
        offset: Float,
        minSize: Float,
        maxSize: Float,
    ): Float {
        val newDimension = originalDimension + offset
        return newDimension.coerceIn(minSize, maxSize)
    }


    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }


}