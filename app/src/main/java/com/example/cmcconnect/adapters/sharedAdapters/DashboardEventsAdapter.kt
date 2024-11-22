package com.example.cmcconnect.adapters.sharedAdapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cmcconnect.R
import com.example.cmcconnect.model.EventDto

class DashboardEventsAdapter: RecyclerView.Adapter<DashboardEventsAdapter.DashboardEventsViewHolder>() {
    private var recentEvents: List<EventDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardEventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_upcoming_events_item, parent, false
        )
        return DashboardEventsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recentEvents.size ?: 0
    }

    override fun onBindViewHolder(holder: DashboardEventsViewHolder, position: Int) {
        val recentEvents = recentEvents[position]
        if (recentEvents != null) {
            holder.bind(recentEvents)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(recentEvent: List<EventDto>) {
        recentEvents = recentEvent
        notifyDataSetChanged()
    }

    inner class DashboardEventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val event_poster: ImageView = itemView.findViewById(R.id.event_poster)
        private val event_title: TextView = itemView.findViewById(R.id.event_title)
        private val event_date: TextView = itemView.findViewById(R.id.event_date)

        fun bind(eventDto: EventDto) {
            event_title.text = eventDto.title
            event_date.text = eventDto.date.toString()

            Glide.with(itemView.context)
                .load(eventDto.poster)
                .placeholder(R.drawable.img_placeholder_for_event)
                .into(event_poster)
        }
    }
}