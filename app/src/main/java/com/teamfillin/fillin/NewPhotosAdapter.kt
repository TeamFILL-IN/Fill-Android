package com.teamfillin.fillin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.databinding.ItemNewPhotosListBinding
import com.teamfillin.fillin.databinding.ItemNextButtonBinding

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
            multi_type1 -> {
                val binding = ItemNewPhotosListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                MultiViewHolder1(binding)
            }
            else -> {
                val binding = ItemNextButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
                MultiViewHolder2(binding)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (photolist[position].type) {
            multi_type1 -> {
                (holder as MultiViewHolder1).bind(photolist[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as MultiViewHolder2).bind(photolist[position])
                holder.setIsRecyclable(false)
            }
        }
    }


    fun replaceList(newList: List<NewPhotosData>) {
        photolist = newList.toList()
        notifyDataSetChanged()
    }


    inner class MultiViewHolder1(private val binding: ItemNewPhotosListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewPhotosData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivNewPhoto)
        }
    }

    inner class MultiViewHolder2(private val binding: ItemNextButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: NewPhotosData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivNextButton)
        }
    }
}