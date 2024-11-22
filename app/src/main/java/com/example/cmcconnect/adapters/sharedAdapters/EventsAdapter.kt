package com.example.cmcconnect.adapters.sharedAdapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cmcconnect.R
import com.example.cmcconnect.model.EventDto
import com.google.android.material.imageview.ShapeableImageView

class EventsAdapter(private val navController: NavController): RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    private var allEvents: List<EventDto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsAdapter.EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_events_item, parent, false
        )
        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsAdapter.EventsViewHolder, position: Int) {
        val allEvents = allEvents[position]
        if (allEvents != null) {
            holder.bind(allEvents)
        }
    }

    override fun getItemCount(): Int {
        return allEvents.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(allEvent: List<EventDto>) {
        allEvents = allEvent
        notifyDataSetChanged()
    }

    inner class EventsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val event_poster: ShapeableImageView = itemView.findViewById(R.id.event_poster)
        private val event_date: TextView = itemView.findViewById(R.id.event_date)
        private val event_title: TextView = itemView.findViewById(R.id.event_title)
        private val event_place: TextView = itemView.findViewById(R.id.event_place)
        private val button_details: Button = itemView.findViewById(R.id.button_details)

        fun bind(eventDto: EventDto) {
            Glide.with(itemView.context)
                .load(eventDto.poster)
                .placeholder(R.drawable.img_placeholder_for_event)
                .into(event_poster)
            val eventDateFormat = "${eventDto.date.dayOfMonth} \n ${eventDto.date.month}"
            event_date.text = eventDateFormat
            event_title.text = eventDto.title
            event_place.text = eventDto.place

            button_details.setOnClickListener {
                navController.navigate(R.id.eventsDetailsFragment, Bundle().apply {
                    putSerializable("clickedEvent", eventDto)
                })
            }
        }
    }
}