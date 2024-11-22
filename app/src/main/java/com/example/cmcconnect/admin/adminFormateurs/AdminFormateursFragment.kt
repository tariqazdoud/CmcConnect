package com.example.cmcconnect.admin.adminFormateurs

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.adapters.adminAdapters.TeachersRvAdapter
import com.example.cmcconnect.databinding.AdminFragmentAdminFormatursBinding
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminFormateursFragment : Fragment() {
    private val adminFormateursViewModel : AdminFormateursViewModel by viewModels()

    private var _binding: AdminFragmentAdminFormatursBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentAdminFormatursBinding.inflate(inflater, container, false)
        val view = binding.root
        val rvTeachers : RecyclerView = binding.formateursRv
        val adapter = TeachersRvAdapter()
        rvTeachers.layoutManager = GridLayoutManager(requireContext(),2)
        rvTeachers.adapter = adapter
        adminFormateursViewModel.teachersLiveData.observe(viewLifecycleOwner){
            data-> adapter.submitList(data)
            Log.d("data","$data")
        }
        UserInInfo.id_pole_fk?.let { adminFormateursViewModel.getFormateursByPoleId(it) }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
