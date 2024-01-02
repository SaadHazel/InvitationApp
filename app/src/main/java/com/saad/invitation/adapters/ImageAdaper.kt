package com.saad.invitation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saad.invitation.R
import com.saad.invitation.databinding.ItemImageBinding
import com.saad.invitation.models.Hit

class ImageAdapter(private val itemList: List<Hit>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val hit = itemList[position]
        holder.bind(hit)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(hit: Hit) {
            binding.imageView1.setOnClickListener {
                onItemClick(hit.largeImageURL)
            }
            // Use Glide to load and display the image
            Glide.with(binding.root.context)
                .load(hit.largeImageURL)
                .centerCrop()
                .placeholder(R.drawable.baseline_cloud_download_24) // You can set a placeholder image
                .into(binding.imageView1)


            binding.executePendingBindings()
        }
    }
}
