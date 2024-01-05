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

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.all_cards)

        viewModel.images.observe(this) { imagelist ->

            val adapter = ImageAdapter(imagelist) {
                onListItemClick(it)
            }
            binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerView.adapter = adapter

        }
    }

    private fun onListItemClick(item: String) {
        val intent = Intent(this@MainActivity, DragActivity::class.java)
        intent.putExtra("message_key", item)
        startActivity(intent)
    }
}