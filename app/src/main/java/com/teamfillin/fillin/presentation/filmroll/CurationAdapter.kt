package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.context.drawableOf
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.response.ResponseFilmRoll
import com.teamfillin.fillin.databinding.ItemCurationBinding
import com.teamfillin.fillin.databinding.ItemCurationFirstBinding
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import timber.log.Timber

private const val CURATION_INFO_TYPE = 1
private const val CURATION_TYPE = 2

class CurationAdapter(
    private val listener: ItemClickListener,
    private val curation: ResponseFilmRoll.Curation
) :
    ListAdapter<ResponseFilmRoll.FilmPhotoInfo, RecyclerView.ViewHolder>(CurationDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> CURATION_INFO_TYPE
            else -> CURATION_TYPE
        }
    }

    class CurationInfoViewHolder(
        private val binding: ItemCurationFirstBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(curation: ResponseFilmRoll.Curation) {
            binding.tvCurationinfo.text = curation.title
        }
    }

    class CurationImageViewHolder(
        private val binding: ItemCurationBinding,
        private val listener: ItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: ResponseFilmRoll.FilmPhotoInfo) {
            Glide.with(itemView.context)
                .load(film.imageUrl)
                .placeholder(itemView.context.drawableOf(R.drawable.ic_launcher_foreground))
                .into(binding.ivCuration)
            binding.root.setOnClickListener {
                listener.onClick(film)
            }
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
                CurationImageViewHolder(binding, listener)
            }
        }
    }

    fun interface ItemClickListener {
        fun onClick(data: ResponseFilmRoll.FilmPhotoInfo)
    }

    override fun getItemCount(): Int = currentList.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (position == 0) {
            true -> {
                (holder as CurationInfoViewHolder).bind(curation)
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