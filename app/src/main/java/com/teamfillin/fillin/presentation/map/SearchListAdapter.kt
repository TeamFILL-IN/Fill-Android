package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.data.response.ResponseSearchInfo
import com.teamfillin.fillin.databinding.ItemLocationInfoBinding

class SearchListAdapter :
    ListAdapter<ResponseSearchInfo.StudioInfo, SearchListAdapter.SearchListViewHolder>(
        DIFFUTIL
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val binding =
            ItemLocationInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class SearchListViewHolder(
        private val binding: ItemLocationInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(searchInfo: ResponseSearchInfo.StudioInfo) {
            binding.location = searchInfo
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<ResponseSearchInfo.StudioInfo>() {
            override fun areItemsTheSame(
                oldItem: ResponseSearchInfo.StudioInfo,
                newItem: ResponseSearchInfo.StudioInfo
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponseSearchInfo.StudioInfo,
                newItem: ResponseSearchInfo.StudioInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}