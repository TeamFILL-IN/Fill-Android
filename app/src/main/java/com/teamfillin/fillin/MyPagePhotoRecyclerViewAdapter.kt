package com.teamfillin.fillin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.databinding.ItemMyPageBinding

class MyPagePhotoRecyclerViewAdapter : RecyclerView.Adapter<MyPagePhotoRecyclerViewAdapter.ViewHolder>()
{
    var photolist = mutableListOf<Photos>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPagePhotoRecyclerViewAdapter.ViewHolder {
        val binding=ItemMyPageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyPagePhotoRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        holder.bind(photolist[position])
    }

    override fun getItemCount() = photolist.size

    class ViewHolder(private val binding: ItemMyPageBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(photo:Photos){
            //TODO : 사진 넣기
        }
    }

}