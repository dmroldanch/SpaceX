package com.aptivist.spacex.presentation.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aptivist.spacex.databinding.LauncherIconItemBinding
import com.aptivist.spacex.domain.IImageLoader

class ImageAdapter(private val imageLoader: IImageLoader) : ListAdapter<String, ImageAdapter.ImageViewHolder>(DiffCallbackImage()) {

    inner class ImageViewHolder(private val itemBinding: LauncherIconItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(value : String) {
            with(itemBinding){
                imageLoader.loadImage(value, ivLaunchIcon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LauncherIconItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffCallbackImage : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}