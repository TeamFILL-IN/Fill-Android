package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.data.response.ResponseStudioPhoto
import com.teamfillin.fillin.databinding.ItemPhotoReviewBinding

class PhotoReviewListAdapter :
    ListAdapter<ResponseStudioPhoto.StudioPhoto, PhotoReviewListAdapter.PhotoReviewListViewHolder>(
        DIFFUTIL
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoReviewListViewHolder {
        val binding =
            ItemPhotoReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoReviewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoReviewListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class PhotoReviewListViewHolder(
        private val binding: ItemPhotoReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(studioPhoto: ResponseStudioPhoto.StudioPhoto) {
            Glide.with(itemView.context).load(studioPhoto.imageUrl).into(binding.ivPhoto)
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<ResponseStudioPhoto.StudioPhoto>() {
            override fun areItemsTheSame(
                oldItem: ResponseStudioPhoto.StudioPhoto,
                newItem: ResponseStudioPhoto.StudioPhoto
            ): Boolean {
                return oldItem.photoId == newItem.photoId
            }

            override fun areContentsTheSame(
                oldItem: ResponseStudioPhoto.StudioPhoto,
                newItem: ResponseStudioPhoto.StudioPhoto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}