package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.core.view.ItemDiffCallback
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ItemFilmRollBinding
import com.teamfillin.fillin.domain.entity.CategoryPhoto

class FilmRollPagingAdapter(
    private val itemClickListener: ItemClickListener
) : PagingDataAdapter<CategoryPhoto, FilmRollPagingAdapter.FilmViewHolder>(
    ItemDiffCallback(
        { old, new -> old.photoId == new.photoId },
        { old, new -> old == new }
    )
) {
    fun interface ItemClickListener {
        fun onClick(data: CategoryPhoto)
    }

    class FilmViewHolder(
        private val binding: ItemFilmRollBinding,
        private val itemClickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(film: CategoryPhoto) {
            Glide.with(itemView.context)
                .load(film.imageUrl)
                .into(binding.ivItemFilmroll)
            binding.root.setOnSingleClickListener {
                itemClickListener.onClick(film)
            }
            binding.btnLike.setOnSingleClickListener {
                binding.btnLike.isSelected = !binding.btnLike.isSelected
            }
        }
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmRollBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return FilmViewHolder(binding, itemClickListener)
    }
}