package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.databinding.ItemPhotoReviewBinding
import com.teamfillin.fillin.domain.entity.StudioImage

class PhotoReviewListAdapter(private val listener: ItemClickListener) :
    ListAdapter<StudioImage, PhotoReviewListAdapter.PhotoReviewListViewHolder>(
        DIFFUTIL
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoReviewListViewHolder {
        val binding =
            ItemPhotoReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoReviewListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: PhotoReviewListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun interface ItemClickListener {
        fun onClick(data: StudioImage)
    }

    class PhotoReviewListViewHolder(
        private val binding: ItemPhotoReviewBinding,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(studioPhoto: StudioImage) {
            Glide.with(itemView.context).load(studioPhoto.imageUrl).into(binding.ivPhoto)
            binding.root.setOnClickListener {
                listener.onClick(studioPhoto)
            }
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<StudioImage>() {
            override fun areItemsTheSame(
                oldItem: StudioImage,
                newItem: StudioImage
            ): Boolean {
                return oldItem.photoId == newItem.photoId
            }

            override fun areContentsTheSame(
                oldItem: StudioImage,
                newItem: StudioImage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}