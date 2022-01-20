package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.data.response.ResponseSearch
import com.teamfillin.fillin.databinding.ItemLocationInfoBinding

class SearchListAdapter(private val listener: ItemClickListener) :
    ListAdapter<ResponseSearch.StudioResponse, SearchListAdapter.SearchListViewHolder>(
        DIFFUTIL
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder {
        val binding =
            ItemLocationInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchListViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    fun interface ItemClickListener {
        fun onClick(data: ResponseSearch.StudioResponse)
    }

    class SearchListViewHolder(
        private val binding: ItemLocationInfoBinding,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(searchInfo: ResponseSearch.StudioResponse) {
            binding.location = searchInfo
            binding.root.setOnClickListener {
                listener.onClick(searchInfo)
            }
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<ResponseSearch.StudioResponse>() {
            override fun areItemsTheSame(
                oldItem: ResponseSearch.StudioResponse,
                newItem: ResponseSearch.StudioResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResponseSearch.StudioResponse,
                newItem: ResponseSearch.StudioResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}