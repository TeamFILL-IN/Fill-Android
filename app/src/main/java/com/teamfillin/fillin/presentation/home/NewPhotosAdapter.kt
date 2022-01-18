package com.teamfillin.fillin.com.teamfillin.fillin.presentation.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ItemNewPhotosListBinding
import com.teamfillin.fillin.databinding.ItemNextButtonBinding
import com.teamfillin.fillin.presentation.category.FilmRollCategoryActivity
import com.teamfillin.fillin.presentation.filmroll.FilmRollActivity
import com.teamfillin.fillin.presentation.home.NewPhotosData

class NewPhotosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photolist = listOf<NewPhotosData>()

    override fun getItemViewType(position: Int): Int {
        return photolist[position].type
    }

    override fun getItemCount(): Int = photolist.size
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            NEW_PHOTOS_TYPE -> {
                val binding = ItemNewPhotosListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                NewPhotosViewHolder(binding)
            }
            else -> {
                val binding = ItemNextButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                NextButtonViewHolder(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (photolist[position].type) {
            NEW_PHOTOS_TYPE -> {
                (holder as NewPhotosViewHolder).bind(photolist[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as NextButtonViewHolder).bind(photolist[position])
                holder.setIsRecyclable(false)
            }
        }
    }


    fun replaceList(newList: List<NewPhotosData>) {
        photolist = newList.toList()
        notifyDataSetChanged()
    }


    class NewPhotosViewHolder(private val binding: ItemNewPhotosListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewPhotosData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivNewPhoto)
        }
    }

    class NextButtonViewHolder(private val binding: ItemNextButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewPhotosData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivNextButton)
            binding.ivNextButton.setOnSingleClickListener {
                val intent = Intent(itemView.context, FilmRollActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }
    }
}