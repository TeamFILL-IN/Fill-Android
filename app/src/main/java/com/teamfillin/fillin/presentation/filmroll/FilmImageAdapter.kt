package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.databinding.ItemCategoryImageBinding
import com.teamfillin.fillin.presentation.filmroll.model.PhotoUiModel

private val DIFF_UTIL = object : DiffUtil.ItemCallback<PhotoUiModel>() {
    override fun areItemsTheSame(oldItem: PhotoUiModel, newItem: PhotoUiModel): Boolean {
        return oldItem.imageUrl == newItem.imageUrl
    }

    override fun areContentsTheSame(oldItem: PhotoUiModel, newItem: PhotoUiModel): Boolean {
        return oldItem == newItem
    }
}

class FilmImageAdapter :
    PagingDataAdapter<PhotoUiModel, FilmImageAdapter.FilmImageViewHolder>(DIFF_UTIL) {
    class FilmImageViewHolder(private val binding: ItemCategoryImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: PhotoUiModel) {
            Glide.with(binding.root)
                .load(film.imageUrl)
                .into(binding.ivFilmroll)
//            binding.btnLike.setOnSingleClickListener {
//                binding.btnLike.isSelected = !binding.btnLike.isSelected
//            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmImageViewHolder {
        val binding = ItemCategoryImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return FilmImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmImageViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}
