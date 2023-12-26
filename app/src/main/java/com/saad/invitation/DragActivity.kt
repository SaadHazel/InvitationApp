package com.saad.invitation


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.saad.invitation.databinding.ActivityDragBinding
import com.saad.invitation.learning.SingleTouchListner


const val DEBUG_TAG = "Gestures"

class DragActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDragBinding
    private lateinit var mDetector: GestureDetectorCompat

    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drag)

        mDetector = GestureDetectorCompat(this, SingleTouchListner(binding.cardView, null))
        val viewImage = binding.image
        val viewText = binding.etText
        val imageString =
            "https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg"

        Glide.with(this).load(imageString).centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground).into(viewImage)

        val singleTouchListner = SingleTouchListner(binding.cardView, mDetector)
        viewImage.setOnTouchListener(singleTouchListner)
        viewText.setOnTouchListener(singleTouchListner)

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


    /*
        private class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float,
            ): Boolean {
                Log.d(DEBUG_TAG, "onFling: $e1 $e2")
                return super.onFling(e1, e2, velocityX, velocityY)
            }


            override fun onDown(event: MotionEvent): Boolean {
                //            Log.d(DEBUG_TAG, "onDown: $event")
                return true
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                Log.d(DEBUG_TAG, "onDoubleTap: $e")

                return super.onDoubleTap(e)
            }

            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
                Log.d(DEBUG_TAG, "onLongPress: $e")
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                Log.d(DEBUG_TAG, "onSingleTapUp: $e")
                return super.onSingleTapUp(e)
            }
        }

    */

    /*   override fun onTouchEvent(event: MotionEvent?): Boolean {
           if (event?.action == MotionEvent.ACTION_DOWN) {
               Log.d(DEBUG_TAG, "inside action down")
               x = event.x
               y = event.y
               Log.d(DEBUG_TAG, "$x")
               Log.d(DEBUG_TAG, "$y")
           }
           if (event?.action == MotionEvent.ACTION_MOVE) {
               dx = event.x - x
               dy = event.y - y

               binding.image.x = binding.image.x + dx
               binding.image.y = binding.image.y + dy

               x = event.x
               y = event.y
           }
           return super.onTouchEvent(event)
       }*/
}