package com.example.cmcconnect.formateur.formateurSeeResources

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
import com.example.cmcconnect.adapters.teacherAdapters.SeeResourcesModuleSpinnerAdapter
import com.example.cmcconnect.adapters.teacherAdapters.SeeResourcesRvAdapter
import com.example.cmcconnect.adapters.teacherAdapters.SeeResourcesSpinnerAdapter
import com.example.cmcconnect.databinding.FormateurFragmentSeeResourcesBinding
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeResourcesFragment : Fragment() {
    private val seeResourcesViewModel: SeeResourcesViewModel by viewModels()

    private var _binding: FormateurFragmentSeeResourcesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FormateurFragmentSeeResourcesBinding.inflate(inflater, container, false)
        val view = binding.root

        val seeResourcesSpinner: Spinner = binding.spinnerGroups
        seeResourcesViewModel.groupsLiveData.observe(viewLifecycleOwner) { group ->
            val adapter = SeeResourcesSpinnerAdapter(requireContext(), group)
            seeResourcesSpinner.adapter = adapter
            Log.d("group", "$group")
        }
        seeResourcesViewModel.getCourByTeacherId(UserInInfo.id)

        val seeResourcesModuleSpinner: Spinner = binding.spinnerModules
        seeResourcesViewModel.modulesLiveData.observe(viewLifecycleOwner) {

                module ->
            val adapterModuleSpinner = SeeResourcesModuleSpinnerAdapter(requireContext(), module)
            seeResourcesModuleSpinner.adapter = adapterModuleSpinner
            Log.d("module", "$module")
        }



        seeResourcesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedGroup = parent?.getItemAtPosition(position) as GroupeDto
                val selectedGroupId = selectedGroup.id
                if (selectedGroupId != null) {
                    seeResourcesViewModel.getModuleByTeacherAndGroupId(
                        UserInInfo.id,
                        selectedGroupId
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //to do
            }

        }
        seeResourcesModuleSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedModule = parent?.getItemAtPosition(position) as ModuleDto
                val selectedModuleId = selectedModule.id
                seeResourcesViewModel.getResourceTeacherAndModuleId(UserInInfo.id,selectedModuleId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //to do
            }

        }
        val rvResources : RecyclerView = binding.rvResources
        rvResources.layoutManager = LinearLayoutManager(requireContext())
        val rvAdapter = SeeResourcesRvAdapter()
        rvResources.adapter = rvAdapter

        seeResourcesViewModel.resourcesLiveDate.observe(viewLifecycleOwner){
            data-> rvAdapter.submitList(data)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
