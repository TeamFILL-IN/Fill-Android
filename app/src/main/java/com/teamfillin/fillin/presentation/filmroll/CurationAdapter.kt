package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.core.content.receive
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.response.ResponseFilmRoll
import com.teamfillin.fillin.databinding.ItemCurationBinding
import com.teamfillin.fillin.databinding.ItemCurationFirstBinding
import timber.log.Timber

private const val CURATION_INFO_TYPE = 1
private const val CURATION_TYPE = 2

class CurationAdapter :
    ListAdapter<ResponseFilmRoll.FilmPhotoInfo, RecyclerView.ViewHolder>(CurationDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> CURATION_INFO_TYPE
            else -> CURATION_TYPE
        }
    }

    class CurationInfoViewHolder(private val binding: ItemCurationFirstBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.tvCuration.text = "따뜻한 사진을 \n원한다면"
        }
    }

    class CurationImageViewHolder(private val binding: ItemCurationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: ResponseFilmRoll.FilmPhotoInfo) {
            Glide.with(binding.root)
                .load(film.imageUrl)
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

    override fun getItemCount(): Int = currentList.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (position == 0) {
            true -> {
                (holder as CurationInfoViewHolder).bind()
            }
            else -> {
                (holder as CurationImageViewHolder).bind(getItem(position - 1))
            }
        }
    }

    private class CurationDiffUtil : DiffUtil.ItemCallback<ResponseFilmRoll.FilmPhotoInfo>() {
        override fun areItemsTheSame(
            oldItem: ResponseFilmRoll.FilmPhotoInfo,
            newItem: ResponseFilmRoll.FilmPhotoInfo
        ) = oldItem.imageUrl == newItem.imageUrl

        override fun areContentsTheSame(
            oldItem: ResponseFilmRoll.FilmPhotoInfo,
            newItem: ResponseFilmRoll.FilmPhotoInfo
        ) = oldItem == newItem
    }
}