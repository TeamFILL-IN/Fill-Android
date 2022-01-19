package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ItemCategoryImageBinding
import com.teamfillin.fillin.databinding.ItemCurationBinding

class FilmImageAdapter() :
    ListAdapter<FilmImageInfo, FilmImageAdapter.FilmImageViewHolder>(FilmImageDiffUtil()) {
    class FilmImageViewHolder(private val binding: ItemCategoryImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmImageInfo) {
            Glide.with(binding.root)
                .load(film.image)
                .into(binding.ivFilmroll)
            binding.btnLike.setOnSingleClickListener {
                binding.btnLike.isSelected = !binding.btnLike.isSelected
            }

        }
    }

    fun interface OnItemClickListener {
        fun onClick(v: View, position: Int)
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
        holder.bind(getItem(position))
    }

    // (1) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    // (2) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener: OnItemClickListener

    private class FilmImageDiffUtil : DiffUtil.ItemCallback<FilmImageInfo>() {
        override fun areItemsTheSame(
            oldItem: FilmImageInfo,
            newItem: FilmImageInfo
        ) =
            (oldItem.image == newItem.image)

        override fun areContentsTheSame(
            oldItem: FilmImageInfo,
            newItem: FilmImageInfo
        ) =
            (oldItem == newItem)
    }
}
