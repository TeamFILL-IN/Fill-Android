package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.data.ResponseLocationInfo
import com.teamfillin.fillin.databinding.ItemLocationInfoBinding

class LocationListAdapter :
    ListAdapter<ResponseLocationInfo, LocationListAdapter.LocationListViewHolder>(
        DIFFUTIL
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val binding =
            ItemLocationInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class LocationListViewHolder(
        private val binding: ItemLocationInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(locationInfo: ResponseLocationInfo) {
            binding.location = locationInfo
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<ResponseLocationInfo>() {
            override fun areItemsTheSame(
                oldItem: ResponseLocationInfo,
                newItem: ResponseLocationInfo
            ): Boolean {
                return oldItem.location == newItem.location
            }

            override fun areContentsTheSame(
                oldItem: ResponseLocationInfo,
                newItem: ResponseLocationInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}