package com.example.cmcconnect.admin.adminAddGroup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cmcconnect.databinding.AdminFragmentAdminAddStudentBinding
import com.example.cmcconnect.model.GroupeDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminAddStudent : Fragment() {
    private val addGroupViewModel: AddGroupViewModel by viewModels()
    private var _binding: AdminFragmentAdminAddStudentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentAdminAddStudentBinding.inflate(inflater, container, false)
        val view = binding.root
        val selectedGroup = arguments?.getSerializable("clickedGroup") as GroupeDto
        binding.addStudentBtn.setOnClickListener {
            val name : String = binding.studentNameTv.text.toString()
            val email: String = binding.studentEmailTv.text.toString()
            val phone : String= binding.studentPhoneTv.text.toString()
            val image =
                "https://hvlzrnryaxhaudwlwvhf.supabase.co/storage/v1/object/public/posetrs/profile.png?t=2024-07-03T10%3A10%3A34.475Z"
            selectedGroup.id?.let { it1 ->
                addGroupViewModel.addStudent(
                    name, email, phone, image,
                    it1, 1
                )
            }
            addGroupViewModel.addStudentStatus.observe(viewLifecycleOwner) { status ->
                if (status == true) {
                    Toast.makeText(requireContext(),"successfully added student", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }else{
                    Toast.makeText(requireContext(),"failed to add group",Toast.LENGTH_SHORT).show()
                }
            }
        }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
