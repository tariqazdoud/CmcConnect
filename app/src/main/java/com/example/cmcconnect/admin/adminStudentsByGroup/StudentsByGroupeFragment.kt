package com.example.cmcconnect.admin.adminStudentsByGroup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.adapters.teacherAdapters.StudentsGroupRvAdapter
import com.example.cmcconnect.databinding.AdminFragmentStudentsByGroupeBinding
import com.example.cmcconnect.model.GroupeDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentsByGroupeFragment : Fragment() {
    private val studentsByGroupViewModel : StudentsByGroupViewModel by viewModels()

    private var _binding: AdminFragmentStudentsByGroupeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentStudentsByGroupeBinding.inflate(inflater, container, false)
        val view =  binding.root
        val backIcon : ImageButton = binding.backIcon
        backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
        val clickedGroup = arguments?.getSerializable("clickedGroup") as GroupeDto
        binding.groupTv.text = clickedGroup.name

        val studentsRv : RecyclerView = binding.studentsRv
        studentsRv.layoutManager = LinearLayoutManager(requireContext())
        val rvAdapter = StudentsGroupRvAdapter()
        studentsRv.adapter = rvAdapter

        studentsByGroupViewModel.studentsLiveData.observe(viewLifecycleOwner){
            data-> rvAdapter.submitList(data)
        }
        clickedGroup.id?.let { studentsByGroupViewModel.getStudentsByGroupId(it) }

        binding.addBtn.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("clickedGroup", clickedGroup)
            }
            findNavController().navigate(R.id.action_studentsByGroupeFragment_to_adminAddStudent,bundle)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
