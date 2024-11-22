package com.example.cmcconnect.shared.eventsDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.cmcconnect.R
import com.example.cmcconnect.databinding.FragmentEventsDetailsBinding
import com.example.cmcconnect.model.EventDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsDetailsFragment : Fragment() {
    private var _binding: FragmentEventsDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val backgroundImage: ImageView = binding.backgroundImage
        val iconBack: ImageView = binding.iconBack
        val eventTitle: TextView = binding.eventTitle
        val eventDate: TextView = binding.eventDate
        val eventTime: TextView = binding.eventTime
        val eventPlace: TextView = binding.eventPlace
        val eventDesc: TextView = binding.tvEventDescription

        val clickedEvent = arguments?.getSerializable("clickedEvent") as EventDto?
        val event_time = "${clickedEvent?.start_time} - ${clickedEvent?.end_time}"
        val event_date = "${clickedEvent?.date?.dayOfMonth} ${clickedEvent?.date?.month} ${clickedEvent?.date?.year}"

        if (clickedEvent != null) {
            Glide.with(view.context)
                .load(clickedEvent.poster)
                .placeholder(R.drawable.img_event_poster_three)
                .into(backgroundImage)
            eventTitle.text = clickedEvent.title
            eventDate.text = event_date
            eventTime.text = event_time
            eventPlace.text = clickedEvent.place
            eventDesc.text = clickedEvent.description
        }

        iconBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return view
    }


}