package com.example.cmcconnect.admin.adminFiliere

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.adapters.adminAdapters.FilieresGroupesSpinnerAdapter
import com.example.cmcconnect.adapters.adminAdapters.GroupsRvAdapter
import com.example.cmcconnect.adapters.teacherAdapters.GroupesRvAdapter
import com.example.cmcconnect.databinding.AdminFragmentFiliereBinding
import com.example.cmcconnect.model.FiliereDto
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class FiliereFragment : Fragment() {
    private val filiereViewModel : FiliereViewModel by viewModels()

    private var _binding: AdminFragmentFiliereBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentFiliereBinding.inflate(inflater, container, false)
        val view = binding.root

        val filiereSpinner : Spinner = binding.spinnerGroups
        val addGroupBtn : Button = binding.addGroupBtn

        filiereViewModel.filieresLiveData.observe(viewLifecycleOwner){
            filieres-> val adapter = FilieresGroupesSpinnerAdapter(requireContext(),filieres)
            filiereSpinner.adapter = adapter

        }
        UserInInfo.id_pole_fk?.let { filiereViewModel.getFilieresByPoleId(it) }
        filiereViewModel.groupsLiveData.observe(viewLifecycleOwner){
            data->
            Log.d("data","$data")

        }

        filiereSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedFiliere = parent?.getItemAtPosition(position) as FiliereDto
                val selectedFiliereId = selectedFiliere.id
                if (selectedFiliereId != null) {
                    filiereViewModel.getGroupsByFiliereId(selectedFiliereId)
                }
                addGroupBtn.setOnClickListener {
                    val bundle = Bundle().apply {
                        putSerializable("selectedFiliere", selectedFiliere)
                    }
                    findNavController().navigate(R.id.action_id_filiereFragment_to_adminAddGroupFragment,bundle)

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //to do
            }

        }

        val rvGroups : RecyclerView = binding.groupsRv
        rvGroups.layoutManager = LinearLayoutManager(requireContext())
        val rvAdapter = GroupsRvAdapter(findNavController())
        rvGroups.adapter = rvAdapter
        filiereViewModel.groupsLiveData.observe(viewLifecycleOwner){
            data-> rvAdapter.submitList(data)
        }





        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
