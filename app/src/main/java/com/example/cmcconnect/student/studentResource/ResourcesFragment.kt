package com.example.cmcconnect.student.studentResource


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.adapters.studentAdapters.ResourcesRvAdapter
import com.example.cmcconnect.adapters.studentAdapters.ResourcesSpinnerAdapter
import com.example.cmcconnect.databinding.FragmentResourcesBinding
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResourcesFragment : Fragment() {
    private val resourcesViewModel: ResourcesViewModel by viewModels()

    private var _binding: FragmentResourcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResourcesBinding.inflate(inflater, container, false)
        val view = binding.root

        val resourcesSpinner: Spinner = binding.spinnerResources


        resourcesViewModel.modulesLiveDate.observe(viewLifecycleOwner, Observer {

                allData ->
            val listModules = mutableListOf<ModuleDto>()

            allData.forEach { coursDto ->
                if (coursDto?.module != null) {
                    listModules.add(coursDto.module)

                }
            }
            Log.d("all module", "$listModules")

            val adapter = ResourcesSpinnerAdapter(requireContext(), listModules)
            resourcesSpinner.adapter = adapter
        })

        UserInInfo.id_groupe_fk?.let { resourcesViewModel.getModulesByGroupId(it) }

        resourcesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedModule = parent?.getItemAtPosition(position) as ModuleDto
                val selectedModuleId = selectedModule.id

                resourcesViewModel.getResourcesByModuleId(selectedModuleId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //to do
            }

        }

        val rvResources: RecyclerView = binding.rvResources
        rvResources.layoutManager = LinearLayoutManager(requireContext())
        val rvAdapter = ResourcesRvAdapter()
        rvResources.adapter = rvAdapter


        resourcesViewModel.resourcesLiveDate.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                rvAdapter.submitList(result)
            }
            Log.d("all resources","$result")
        })




        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
