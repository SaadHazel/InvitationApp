package com.saad.invitation.views


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.saad.invitation.R
import com.saad.invitation.databinding.ActivityDragBinding
import com.saad.invitation.learning.ImageSaver
import com.saad.invitation.learning.SingleTouchListner
import com.saad.invitation.learning.debug_tag
import com.saad.invitation.learning.isViewBeingDragged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


const val DEBUG_TAG = "Gestures"

class DragActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDragBinding

    //    private lateinit var mDetector: GestureDetectorCompat
    private lateinit var cardView: CardView

    @RequiresApi(Build.VERSION_CODES.R)
    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drag)
        cardView = binding.cardView
//        mDetector = GestureDetectorCompat(this, SingleTouchListner(null, null))

        Log.d(debug_tag, "$isViewBeingDragged")
        val cardView = binding.cardView
        val viewImage = binding.image
        val viewText = binding.etText
        val viewTextTwo = binding.etText2
        binding.btnImage.visibility = View.GONE
        val message = intent.getStringExtra("message_key")
        val imageString =
            "https://png.pngtree.com/background/20220724/original/pngtree-golden-green-mandala-art-background-with-border-invitation-card-wedding-islamic-picture-image_1740112.jpg"


        Glide.with(this)
            .load(imageString)
            .fitCenter()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(viewImage)

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

//        viewImage.setOnTouchListener(SingleTouchListner())
        /*       val zoomTouchListener = ZoomTouchListener(viewText, viewImage)

               viewText.setOnTouchListener(zoomTouchListener)*/

//        viewImage.setOnTouchListener(SingleTouchListner())
        viewText.visibility = View.GONE
        viewTextTwo.visibility = View.GONE
        viewImage.visibility = View.GONE
        val pdfDocument = PdfDocument()

        val inflater = layoutInflater
        val secondLayout: View = inflater.inflate(R.layout.view_text_editor, null)
        val textViewFromSecondLayout = secondLayout.findViewById<ImageView>(R.id.img_right_top)

        textViewFromSecondLayout.setOnClickListener {
            Toast.makeText(this, "Click on the edge", Toast.LENGTH_SHORT).show()
        }


        /* val frameLayout = findViewById<FrameLayout>(R.id.frmBorderText)
         val textView = findViewById<TextView>(R.id.tvPhotoEditorText)
         Log.d(debug_tag, "Frame layout: ${frameLayout}")
         Log.d(debug_tag, "TextView: ${textView}")
         frameLayout?.let {
             val zoomTouchListener =
                 ZoomTouchListener(textView ?: TextView(this).apply { text = "dummy" })
             it.setOnTouchListener(zoomTouchListener)
         }*/


        /* if (!isViewBeingDragged) {
             Log.d(debug_tag, "$isViewBeingDragged")
             viewImage.setOnTouchListener(singleTouchListener)
         }

         if (isViewBeingDragged) {
             Log.d(debug_tag, "$isViewBeingDragged")
             viewTextTwo.setOnTouchListener(null)
         } else {
             viewTextTwo.setOnTouchListener(singleTouchListener)
         }

         if (isViewBeingDragged) {
             viewText.setOnTouchListener(null)
         } else {
             viewText.setOnTouchListener(singleTouchListener)
         }*/

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
                    val yourCardView: CardView = binding.cardView
                    val bitmap = generateBitmapFromView(yourCardView)
                    CoroutineScope(Dispatchers.IO).launch {
                        savePDF(bitmap)
                    }
//                    saveImage(bitmap)

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

            newImageView.setOnTouchListener(SingleTouchListner())

            cardView.addView(newImageView)

        }
    }

    private suspend fun savePDF(bitmap: Bitmap) {
        withContext(Dispatchers.IO) {
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 595, 842, true)

            val pdfDocument = PdfDocument()
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()

            val page = pdfDocument.startPage(pageInfo)
            page.canvas.drawBitmap(scaledBitmap, 0F, 0F, null)
            pdfDocument.finishPage(page)

            val filePath = File(this@DragActivity.getExternalFilesDir(null), "bitmapPdf.pdf")
            pdfDocument.writeTo(FileOutputStream(filePath))
            pdfDocument.close()

            withContext(Dispatchers.Main) {
                Toast.makeText(this@DragActivity, "Pdf Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun saveImage(bitmap: Bitmap) {
        val imageSaver = ImageSaver(this)
        val savedImagePath = imageSaver.saveImage(bitmap)

        if (savedImagePath != null) {
            Toast.makeText(this, "Image saved: $savedImagePath", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generateBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
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
        newTextView.setOnTouchListener(SingleTouchListner())
        val container = binding.cardView
        container.addView(newTextView)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }


}