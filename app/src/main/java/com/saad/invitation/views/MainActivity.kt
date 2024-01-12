package com.saad.invitation.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.saad.invitation.R
import com.saad.invitation.databinding.ActivityMainBinding
import com.saad.invitation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
  

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        /* viewModel.designs.observe(this) { design ->
             val adapter = ImageAdapter(design) { background, documentId ->
                 onListItemClick(background, documentId)
             }
             binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
             binding.recyclerView.adapter = adapter


         }
         viewModel.images.observe(this) { imagelist ->

             *//*  val adapter = ImageAdapter(imagelist) {
                  onListItemClick(it)
              }
              binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
              binding.recyclerView.adapter = adapter*//*

        }
    }

    private fun onListItemClick(item: String, id: String) {
        val intent = Intent(this@MainActivity, DragActivity::class.java)
        intent.putExtra("document_background", item)
        intent.putExtra("document_key", id)
        startActivity(intent)
    }*/
    }
}