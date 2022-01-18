package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.data.response.ResponsePhotoReviewInfo
import com.teamfillin.fillin.databinding.ItemPhotoReviewBinding

class PhotoReviewListAdapter :
    RecyclerView.Adapter<PhotoReviewListAdapter.PhotoReviewListViewHolder>() {
    private val photoList = mutableListOf<ResponsePhotoReviewInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoReviewListViewHolder {
        val binding =
            ItemPhotoReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoReviewListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoReviewListViewHolder, position: Int) {
        holder.onBind(photoList[position])
    }

    override fun getItemCount(): Int = photoList.size

    fun setItem(newItems: List<ResponsePhotoReviewInfo>) {
        photoList.clear()
        photoList.addAll(newItems)
        notifyDataSetChanged()
    }

    class PhotoReviewListViewHolder(
        private val binding: ItemPhotoReviewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(photoReview: ResponsePhotoReviewInfo) {
            binding.ivPhoto.setImageResource(photoReview.photo)
        }
    }
}