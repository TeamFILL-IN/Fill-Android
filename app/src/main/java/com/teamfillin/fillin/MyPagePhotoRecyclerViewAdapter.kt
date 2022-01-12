package com.teamfillin.fillin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.databinding.ItemMyPageBinding

class MyPagePhotoRecyclerViewAdapter :
    RecyclerView.Adapter<MyPagePhotoRecyclerViewAdapter.ViewHolder>() {
    private var photoList = listOf<Photos>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemMyPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(photoList[position])
    }

    override fun getItemCount() = photoList.size

    fun replaceList(newList:List<Photos>){

        photoList=newList.toList()
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMyPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photos) {
            //TODO by현지 : 사진 넣기
        }
    }

}