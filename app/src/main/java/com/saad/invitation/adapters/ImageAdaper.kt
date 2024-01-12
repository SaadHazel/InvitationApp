package com.saad.invitation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saad.invitation.R
import com.saad.invitation.databinding.ItemImageBinding
import com.saad.invitation.models.CardDesignModel

class ImageAdapter(
    private val itemList: List<CardDesignModel>,
    private val onItemClick: (String, String) -> Unit,
) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val document = itemList[position]
        holder.bind(document)
    }

    override fun getItemCount(): Int = itemList.size

    inner class ImageViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(document: CardDesignModel) {
            binding.imageView1.setOnClickListener {
                onItemClick(document.background, document.id)
            }
            // Use Glide to load and display the image
            Glide.with(binding.root.context)
                .load(document.background)
                .centerInside()
                .placeholder(R.drawable.baseline_cloud_download_24) // You can set a placeholder image
                .into(binding.imageView1)


            binding.executePendingBindings()
        }
    }
}
