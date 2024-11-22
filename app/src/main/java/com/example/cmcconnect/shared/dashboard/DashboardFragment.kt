package com.example.cmcconnect.shared.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.adapters.sharedAdapters.DashboardEventsAdapter
import com.example.cmcconnect.adapters.sharedAdapters.DashboardResourcesAdapter
import com.example.cmcconnect.databinding.FragmentDashboardBinding
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {
    private val dashboardViewModel: DashboardViewModel by viewModels()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root


        val seeAllEvents: TextView = binding.tvVoirToutEvents
        seeAllEvents.setOnClickListener {
            findNavController().navigate(R.id.action_id_dashboardFragment_to_id_eventsFragment)
        }

        val eventsRecyclerView: RecyclerView = binding.recyclerViewEvents
        eventsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val eventsAdapter = DashboardEventsAdapter()
        eventsRecyclerView.adapter = eventsAdapter

        dashboardViewModel.recentEventsLiveData.observe(viewLifecycleOwner) { recentEvents ->
            if (recentEvents != null) {
                eventsAdapter.submitList(recentEvents)
            }
            Log.d("recent events", "$recentEvents")
        }

        dashboardViewModel.getRecentEvents()


        val seeAllResources: TextView = binding.tvVoirToutRessources
        seeAllResources.setOnClickListener {
            if (UserInInfo.id_type_user_fk == 1){
                findNavController().navigate(R.id.id_resourcesFragment)
            }else{
                findNavController().navigate(R.id.id_seeResourcesFragment)
            }

        }

        val resourcesRecyclerView: RecyclerView = binding.recyclerViewRessources
        resourcesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val resourcesAdapter = DashboardResourcesAdapter()
        resourcesRecyclerView.adapter = resourcesAdapter
        if (UserInInfo.id_type_user_fk == 1) {
            dashboardViewModel.modulesLiveDate.observe(viewLifecycleOwner, Observer { allData ->
                val listModuleId = mutableListOf<Int>()
                allData.forEach { coursDto ->
                    if (coursDto?.module != null) {
                        listModuleId.add(coursDto.module.id)
                    }
                }

                Log.d("all module", "$listModuleId")

                dashboardViewModel.getResourcesByModuleId(listModuleId)


            })
        } else if (UserInInfo.id_type_user_fk == 2) {
            val listModuleId2 = mutableListOf<Int>()
            listModuleId2.add(1)
            listModuleId2.add(2)
            listModuleId2.add(3)
            listModuleId2.add(4)

            dashboardViewModel.getResourcesByModuleId(listModuleId2)
        }
        dashboardViewModel.resultsLiveData.observe(viewLifecycleOwner, Observer { data ->
            val listResources = mutableListOf<RessourceDto>()
            data.forEach { elem -> elem.forEach { resource -> listResources.add(resource) } }
            Log.d("recent resources", "$listResources")
            resourcesAdapter.submitList(listResources)
        })


        UserInInfo.id_groupe_fk?.let { dashboardViewModel.getModulesByGroupId(it) }


        return view
    }

}