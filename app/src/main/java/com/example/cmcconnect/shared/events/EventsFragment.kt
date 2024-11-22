package com.example.cmcconnect.shared.events

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.adapters.sharedAdapters.EventsAdapter
import com.example.cmcconnect.databinding.FragmentEventsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : Fragment() {
    private val eventsViewModel: EventsViewModel by viewModels()
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        val view = binding.root


        val rvEvents: RecyclerView = binding.rvEvents
        rvEvents.layoutManager = LinearLayoutManager(requireContext())
        val adapter = EventsAdapter(findNavController())
        rvEvents.adapter = adapter

        eventsViewModel.allEventsLiveData.observe(viewLifecycleOwner) {
                allEvents ->
            if (allEvents  != null) {
                adapter.submitList(allEvents)
            }
            Log.d("all vote","$allEvents ")
        }

        eventsViewModel.getAllEvents()

        return view
    }


}