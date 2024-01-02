package com.saad.invitation.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.saad.invitation.R
import com.saad.invitation.adapters.ImageAdapter
import com.saad.invitation.databinding.AllCardsBinding
import com.saad.invitation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: AllCardsBinding
    private val viewModel by viewModel<MainViewModel>()

    /*    private var x: Float = 0.0F
    private var y: Float = 0.0F
    private var dx: Float = 0.0F
    private var dy: Float = 0.0F*/

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.all_cards)
        /* val editText = binding.etUpdate

         binding.btnBold.setOnClickListener {
             updateText(editText, Typeface.BOLD)
         }
         binding.btnItalic.setOnClickListener {
             updateText(editText, Typeface.ITALIC)
         }
         binding.btnUnderline.setOnClickListener {
             underlineText(editText)
         }
         binding.btnLeft.setOnClickListener {
             alignText(editText, View.TEXT_ALIGNMENT_TEXT_START)
         }
         binding.btnRight.setOnClickListener {
             alignText(editText, View.TEXT_ALIGNMENT_TEXT_END)
         }
         binding.btnGoto.setOnClickListener {
             val intent = Intent(this, DragActivity::class.java)
             startActivity(intent)
         }
         binding.button4.setOnTouchListener { _, event ->
             when (event.action) {
                 MotionEvent.ACTION_DOWN -> {
                     alignText(editText, View.TEXT_ALIGNMENT_CENTER)
                     x = event.rawX
                     y = event.rawY
                     Log.d(DEBUG_TAG, "Action Down")
                     true
                 }

                 MotionEvent.ACTION_MOVE -> {
                     //Calculating distant moved
                     dx = event.x - x
                     dy = event.y - y

                     //Updating buttons position
                     binding.button4.x = binding.button4.x + dx
                     binding.button4.y = binding.button4.y + dy

                     //Saving new coordinates
                     x = event.x
                     y = event.y
                     Log.d(DEBUG_TAG, "ACTION_MOVE")
                     true
                 }

                 MotionEvent.ACTION_UP -> {
                     Log.d(DEBUG_TAG, "ACTION_UP")
                     true
                 }

                 MotionEvent.ACTION_CANCEL -> {
                     Log.d(DEBUG_TAG, "ACTION_CANCEL")
                     true
                 }

                 MotionEvent.ACTION_OUTSIDE -> {
                     Log.d(DEBUG_TAG, "ACTION_OUTSIDE")
                     true
                 }

                 else -> super.onTouchEvent(event)
             }
         }
         binding.btnCenter.setOnTouchListener { view, event ->
             when (event.action) {
                 MotionEvent.ACTION_DOWN -> {
                     alignText(editText, View.TEXT_ALIGNMENT_CENTER)
                     x = event.rawX
                     y = event.rawY
                     Log.d(DEBUG_TAG, "Action Down")
                     true
                 }

                 MotionEvent.ACTION_MOVE -> {
                     //Calculating distant moved
                     dx = event.rawX - x
                     dy = event.rawY - y

                     //Updating buttons position
                     view.x = view.x + dx
                     view.y = view.y + dy

                     //Saving new coordinates
                     x = event.rawX
                     y = event.rawY
                     Log.d(DEBUG_TAG, "ACTION_MOVE")
                     true
                 }

                 MotionEvent.ACTION_UP -> {
                     Log.d(DEBUG_TAG, "ACTION_UP")
                     true
                 }

                 else -> super.onTouchEvent(event)
             }
         }*/

        viewModel.images.observe(this) { imagelist ->
            val adapter = ImageAdapter(imagelist.hits) {
                onListItemClick(it)
            }
            binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerView.adapter = adapter
            /*  imagelist.hits.forEach { hit ->
                  val largeImageUrl = hit.largeImageURL
                  Log.d(debug_tag, "Large Image URL: $largeImageUrl")
              }*/
        }
    }

    private fun onListItemClick(item: String) {
        val intent = Intent(this@MainActivity, DragActivity::class.java)
        intent.putExtra("message_key", item)
        startActivity(intent)
    }
}