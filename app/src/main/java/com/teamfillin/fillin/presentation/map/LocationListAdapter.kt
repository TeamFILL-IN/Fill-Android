package com.teamfillin.fillin.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.data.ResponseLocationInfo
import com.teamfillin.fillin.databinding.ItemLocationInfoBinding

class LocationListAdapter : RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder>() {
    private val locationList = mutableListOf<ResponseLocationInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {
        val binding =
            ItemLocationInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationListViewHolder, position: Int) {
        holder.onBind(locationList[position], position)
    }

    fun setItem(newItems: List<ResponseLocationInfo>) {
        locationList.clear()
        locationList.addAll(newItems.toList())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = locationList.size

    inner class LocationListViewHolder(
        private val binding: ItemLocationInfoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(locationInfo: ResponseLocationInfo, position: Int) {
            binding.location = locationInfo
        }
    }
}