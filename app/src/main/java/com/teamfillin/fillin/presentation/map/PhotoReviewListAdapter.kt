package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.data.response.ResponseStudioPhoto
import com.teamfillin.fillin.databinding.ItemPhotoReviewBinding
import timber.log.Timber

class PhotoReviewListAdapter :
    RecyclerView.Adapter<PhotoReviewListAdapter.PhotoReviewListViewHolder>() {
    private val photoList = mutableListOf<ResponseStudioPhoto.StudioPhoto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoReviewListViewHolder {
        val binding =
            ItemPhotoReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoReviewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoReviewListViewHolder, position: Int) {
        Timber.d("Nunu ${photoList[position]}")
        holder.onBind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    fun setItem(newItems: List<ResponseStudioPhoto.StudioPhoto>) {
        photoList.clear()
        photoList.addAll(newItems)
        notifyDataSetChanged()
    }

    class PhotoReviewListViewHolder(
        private val binding: ItemPhotoReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(studioPhoto: ResponseStudioPhoto.StudioPhoto) {
            Glide.with(itemView.context).load(studioPhoto.imageUrl).into(binding.ivPhoto)
        }
    }
}