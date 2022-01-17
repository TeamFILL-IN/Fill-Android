package com.teamfillin.fillin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.databinding.ItemCurationBinding

class CurationAdapter():
    ListAdapter<CurationInfo, CurationAdapter.CurationViewHolder>(CurationDiffUtil()) {

    class CurationViewHolder(private val binding: ItemCurationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: CurationInfo) {
            Glide.with(binding.root)
                .load(film.image)
                .into(binding.ivCuration)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurationViewHolder {
        val binding = ItemCurationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return CurationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class CurationDiffUtil : DiffUtil.ItemCallback<CurationInfo>() {
        override fun areItemsTheSame(
            oldItem: CurationInfo,
            newItem: CurationInfo
        ) =
            (oldItem.image == newItem.image)

        override fun areContentsTheSame(
            oldItem: CurationInfo,
            newItem: CurationInfo
        ) =
            (oldItem == newItem)
    }
}