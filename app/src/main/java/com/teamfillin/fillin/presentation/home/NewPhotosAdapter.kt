package com.teamfillin.fillin.presentation.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.response.ResponseNewPhotoInfo
import com.teamfillin.fillin.databinding.ItemNewPhotosListBinding
import com.teamfillin.fillin.databinding.ItemNextButtonBinding
import com.teamfillin.fillin.presentation.filmroll.FilmRollActivity
import timber.log.Timber

class NewPhotosAdapter(
    private val listener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photolist = listOf<ResponseNewPhotoInfo.Photo>()

    override fun getItemViewType(position: Int): Int {
        return when {
            position < photolist.size -> NEW_PHOTOS_TYPE
            else -> NEXT_BUTTON_TYPE
        }
    }

    override fun getItemCount(): Int = photolist.size + 1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            NEW_PHOTOS_TYPE -> {
                val binding = ItemNewPhotosListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                NewPhotosViewHolder(binding, listener)
            }
            else -> {
                val binding = ItemNextButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                NextButtonViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position < photolist.size) {
            true -> {
                Timber.d("Data ${photolist[position]}")
                (holder as NewPhotosViewHolder).bind(photolist[position])
            }
            else -> {
                (holder as NextButtonViewHolder).bind()
            }
        }
    }

    fun replaceList(newList: List<ResponseNewPhotoInfo.Photo>) {
        photolist = newList.toList()
        notifyDataSetChanged()
    }

    fun interface ItemClickListener {
        fun onClick(data: ResponseNewPhotoInfo.Photo)
    }


    class NewPhotosViewHolder(
        private val binding: ItemNewPhotosListBinding,
        private val listener: ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResponseNewPhotoInfo.Photo) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(binding.ivNewPhoto)
            binding.root.setOnSingleClickListener {
                listener.onClick(data)
            }
        }
    }

    class NextButtonViewHolder(private val binding: ItemNextButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.btnNext.setOnSingleClickListener {
                val intent = Intent(itemView.context, FilmRollActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }
}