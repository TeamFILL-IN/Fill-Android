package com.teamfillin.fillin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamfillin.fillin.databinding.ItemNewPhotosListBinding

class NewPhotosAdapter : RecyclerView.Adapter<NewPhotosAdapter.NewPhotosViewHolder>() {

    private var photolist = listOf<NewPhotosData>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewPhotosViewHolder {
        val binding = ItemNewPhotosListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NewPhotosViewHolder(binding)
    }

    fun replaceList(newList: List<NewPhotosData>) {
        photolist = newList.toList()
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: NewPhotosViewHolder, position: Int) {
        holder.onBind(photolist[position])
    }

    override fun getItemCount(): Int = photolist.size
    class NewPhotosViewHolder(private val binding: ItemNewPhotosListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: NewPhotosData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivNewPhoto)
        }
    }
}