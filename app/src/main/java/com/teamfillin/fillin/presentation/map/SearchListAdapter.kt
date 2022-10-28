package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.databinding.ItemLocationInfoBinding
import com.teamfillin.fillin.domain.entity.StudioAddress

class SearchListAdapter(private val listener: ItemClickListener) :
    ListAdapter<StudioAddress, SearchListAdapter.SearchListViewHolder>(
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
        fun onClick(data: StudioAddress)
    }

    class SearchListViewHolder(
        private val binding: ItemLocationInfoBinding,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(searchInfo: StudioAddress) {
            binding.location = searchInfo
            binding.root.setOnClickListener {
                listener.onClick(searchInfo)
            }
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<StudioAddress>() {
            override fun areItemsTheSame(
                oldItem: StudioAddress,
                newItem: StudioAddress
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: StudioAddress,
                newItem: StudioAddress
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}