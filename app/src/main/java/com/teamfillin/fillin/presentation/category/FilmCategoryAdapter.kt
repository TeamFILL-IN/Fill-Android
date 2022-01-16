package com.teamfillin.fillin.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.data.ResponseLocationInfo
import com.teamfillin.fillin.databinding.ItemCategoryInfoBinding
import com.teamfillin.fillin.databinding.ItemLocationInfoBinding
import com.teamfillin.fillin.presentation.category.FilmCategoryAdapter
import com.teamfillin.fillin.presentation.category.FilmCategoryAdapter.Companion.DIFFUTIL
import com.teamfillin.fillin.presentation.map.LocationListAdapter

class FilmCategoryAdapter :
    ListAdapter<CategoryInfo, FilmCategoryAdapter.FilmListViewHolder>(
        DIFFUTIL
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FilmListViewHolder {
        val binding =
            ItemCategoryInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class FilmListViewHolder(
        private val binding: ItemCategoryInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(categoryInfo: CategoryInfo) {
            binding.category = categoryInfo
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<CategoryInfo>() {
            override fun areItemsTheSame(
                oldItem: CategoryInfo,
                newItem: CategoryInfo
            ): Boolean {
                return oldItem.film == newItem.film
            }

            override fun areContentsTheSame(
                oldItem: CategoryInfo,
                newItem: CategoryInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}




