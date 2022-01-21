package com.teamfillin.fillin.presentation.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.data.response.ResponseFilmType
import com.teamfillin.fillin.data.response.ResponseStudioPhoto
import com.teamfillin.fillin.databinding.ItemCategoryInfoBinding
import com.teamfillin.fillin.presentation.category.FilmCategoryAdapter
import com.teamfillin.fillin.presentation.category.FilmCategoryAdapter.Companion.DIFFUTIL
import com.teamfillin.fillin.presentation.map.PhotoReviewListAdapter


class FilmCategoryAdapter(private val listener: ItemClickListener) :
    ListAdapter<ResponseFilmType.Films, FilmCategoryAdapter.FilmListViewHolder>(DIFFUTIL) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmListViewHolder {
        val binding =
            ItemCategoryInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun interface ItemClickListener {
        fun onClick(data: ResponseFilmType.Films)
    }

    class FilmListViewHolder(
        private val binding: ItemCategoryInfoBinding,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(fileType: ResponseFilmType.Films) {
            binding.category = fileType
            binding.root.setOnClickListener {
                listener.onClick(fileType)
            }
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<ResponseFilmType.Films>() {
            override fun areItemsTheSame(
                oldItem: ResponseFilmType.Films,
                newItem: ResponseFilmType.Films
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: ResponseFilmType.Films,
                newItem: ResponseFilmType.Films
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}