package com.saad.invitation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.saad.invitation.databinding.ActivityDragBinding

const val DEBUG_TAG = "Gestures"

class DragActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDragBinding

    //    private lateinit var mDetector: GestureDetectorCompat
//    private var x: Float = 0.0F
//    private var y: Float = 0.0F
//    private var dx: Float = 0.0F
//    private var dy: Float = 0.0F


    @SuppressLint("ClickableViewAccessibility", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drag)

        val view = binding.image
//        view.setImageDrawable(resources.getDrawable(R.drawable.ic_launcher_background, null))
        view.setOnTouchListener(MultiTouchImageListener())
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