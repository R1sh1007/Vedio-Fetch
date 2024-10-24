package com.example.assignment.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.assignment.R
import com.example.assignment.data.ListItemsModel
import com.example.assignment.databinding.VedioRowItemBinding
import android.view.animation.AnimationUtils
/**
 * Created by Rishi Porwal
 */
class VedioAdapter(private var items: List<ListItemsModel>, private val onClick: (ListItemsModel) -> Unit) :RecyclerView.Adapter<VedioAdapter.ViewHolder>(){

  inner  class ViewHolder(val binding: VedioRowItemBinding):RecyclerView.ViewHolder(binding.root)
    var animationFinishPosition=-1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val binding=VedioRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
       return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder.binding){
           videoTitle.text= items[position].title
           duration.text = formatDuration(items[position].duration)
           Glide.with(root.context)
               .load(items[position].uri)
               .placeholder(R.drawable.vdo_placeholder)
               .into(videoThumbnail)

           if (position>animationFinishPosition){
               val animation = AnimationUtils.loadAnimation(root.context, android.R.anim.slide_in_left)
               root.startAnimation(animation)
               animationFinishPosition=position
           }

           root.setOnClickListener {
               val animation = AnimationUtils.loadAnimation(root.context, R.anim.animation_out)
               it.startAnimation(animation)
               onClick(items[position])
           }

       }
    }

  private fun formatDuration(durationMillis: Long): String {
    val seconds = (durationMillis / 1000) % 60
    val minutes = (durationMillis / (1000 * 60)) % 60
    return String.format("%02d:%02d", minutes, seconds)
  }


}
