package com.sugarspoon.eventcheckin.ui.events

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sugarspoon.data.model.entity.EventEntity
import com.sugarspoon.eventcheckin.databinding.ItemEventBinding
import com.sugarspoon.eventcheckin.utils.loadImage


class EventsAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var seeMoreClick: (String) -> Unit = {}

    var list : List<EventEntity> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(context), parent, false)
        return EventViewHolder(binding.root)
    }

     inner class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(data: EventEntity) = ItemEventBinding.bind(itemView).run {

            itemTitleTv.text = data.title
            itemSeeMoreTv.setOnClickListener {
                seeMoreClick.invoke(data.id)
            }
           itemPictureIv.loadImage(
               context = context,
               url = data.image,
               onLoading = { isLoading ->
                   if (isLoading) {
                       itemLoadingPb.visibility = View.VISIBLE
                   } else {
                       itemLoadingPb.visibility = View.GONE
                   }
               },
               onSuccess = {
                   itemPictureIv.visibility = View.VISIBLE
                   itemDescriptionTv.visibility = View.GONE
               },
               onError = {
                   itemPictureIv.visibility = View.INVISIBLE
                   itemDescriptionTv.visibility = View.VISIBLE
                   itemDescriptionTv.text = data.description
               }
           )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventViewHolder).bind(list[position])
    }
}