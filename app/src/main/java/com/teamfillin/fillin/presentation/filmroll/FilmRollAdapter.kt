package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.databinding.ItemFilmRollBinding

class FilmRollAdapter() :
    ListAdapter<FilmrollInfo, FilmRollAdapter.FilmRollViewHolder>(FilmRollDiffUtil()) {

    class FilmRollViewHolder(private val binding: ItemFilmRollBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmrollInfo) {
            Glide.with(binding.root)
                .load(film.image)
                .into(binding.ivItemFilmroll)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmRollViewHolder {
        val binding = ItemFilmRollBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return FilmRollViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmRollViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class FilmRollDiffUtil : DiffUtil.ItemCallback<FilmrollInfo>() {
        override fun areItemsTheSame(
            oldItem: FilmrollInfo,
            newItem: FilmrollInfo
        ) =
            (oldItem.image == newItem.image)

        override fun areContentsTheSame(
            oldItem: FilmrollInfo,
            newItem: FilmrollInfo
        ) =
            (oldItem == newItem)
    }
}