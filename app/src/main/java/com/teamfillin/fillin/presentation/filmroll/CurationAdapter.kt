package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ItemCurationBinding
import com.teamfillin.fillin.databinding.ItemCurationFirstBinding

class CurationAdapter() :
    ListAdapter<CurationInfo, RecyclerView.ViewHolder>(CurationDiffUtil()) {


    class CurationInfoViewHolder(private val binding: ItemCurationFirstBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: CurationInfo) {
            Glide.with(binding.root)
                .load(film.image)
                .into(binding.ivCurationfirst)
            binding.tvCuration.text = "따뜻한 사진을\n원한다면"
        }
    }

    class CurationImageViewHolder(private val binding: ItemCurationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: CurationInfo) {
            Glide.with(binding.root)
                .load(film.image)
                .into(binding.ivCuration)
            binding.btnLike.setOnSingleClickListener {
                binding.btnLike.isSelected = !binding.btnLike.isSelected
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            CURATION_INFO_TYPE -> {
                val binding = ItemCurationFirstBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                CurationInfoViewHolder(binding)
            }
            else -> {
                val binding = ItemCurationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                CurationImageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == CURATION_INFO_TYPE) {
            (holder as CurationInfoViewHolder).bind(currentList[position])
        } else {
            (holder as CurationImageViewHolder).bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
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