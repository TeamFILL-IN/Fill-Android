package com.teamfillin.fillin.presentation.filmroll

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
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
import com.teamfillin.fillin.presentation.filmroll.CurationAdapter.CurationViewHolder

private const val CURATION_INFO_TYPE = 1
private const val CURATION_TYPE = 2

class CurationAdapter(
    private val curation: ResponseFilmRoll.Curation,
    private val listener: ItemClickListener
) : ListAdapter<ResponseFilmRoll.FilmPhotoInfo, CurationViewHolder>(
    CurationDiffUtil()
) {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> CURATION_INFO_TYPE
            else -> CURATION_TYPE
        }
    }

    abstract class CurationViewHolder(
        protected val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(input: CurationInput)
    }

    class CurationInfoViewHolder(
        binding: ItemCurationFirstBinding
    ) : CurationViewHolder(binding) {
        override fun bind(input: CurationInput) {
            if (input is CurationInput.Curation) {
                (binding as ItemCurationFirstBinding).tvCurationinfo.text = input.data.title
            }
        }
    }

    class CurationImageViewHolder(
        binding: ItemCurationBinding,
        private val listener: ItemClickListener
    ) : CurationViewHolder(binding) {
        override fun bind(input: CurationInput) {
            if (input is CurationInput.Film) {
                with(binding as ItemCurationBinding) {
                    Glide.with(itemView.context)
                        .load(input.data.imageUrl)
                        .placeholder(itemView.context.drawableOf(R.drawable.ic_launcher_foreground))
                        .into(ivCuration)
                    binding.root.setOnClickListener {
                        listener.onClick(input.data)
                    }
                    // TODO by Nunu 좋아요 기능 원복
//                    binding.btnLike.setOnSingleClickListener {
//                        binding.btnLike.isSelected = !binding.btnLike.isSelected
//                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurationViewHolder {
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

    override fun onBindViewHolder(holder: CurationViewHolder, position: Int) {
        holder.bind(if (position == 0) curation.toInput() else getItem(position - 1).toInput())
    }

    private fun ResponseFilmRoll.Curation.toInput() = CurationInput.Curation(this)
    private fun ResponseFilmRoll.FilmPhotoInfo.toInput() = CurationInput.Film(this)

    sealed class CurationInput {
        data class Curation(val data: ResponseFilmRoll.Curation) : CurationInput()
        data class Film(val data: ResponseFilmRoll.FilmPhotoInfo) : CurationInput()
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