package com.example.cmcconnect.formateur.formateurGroupStudents

import android.os.Bundle
import android.util.Log
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
import com.example.cmcconnect.databinding.FormateurFragmentGropeStudentsBinding
import com.example.cmcconnect.model.GroupeDto
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupeStudentsFragment : Fragment() {
    private val groupStudentsViewModel : GroupStudentsViewModel by viewModels()

    private var _binding: FormateurFragmentGropeStudentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FormateurFragmentGropeStudentsBinding.inflate(inflater, container, false)
        val view =  binding.root
        val backIcon : ImageButton = binding.backIcon
        backIcon.setOnClickListener {
            findNavController().navigateUp()
        }
        val clickedGroup = arguments?.getSerializable("clickedGroup") as GroupeDto
        binding.groupTv.text = clickedGroup.name
        binding.filiereTv.text = clickedGroup.filiere?.name.toString()
        Log.d("id group", "${clickedGroup.id}")

        val studentsRv : RecyclerView = binding.studentsRv
        studentsRv.layoutManager = LinearLayoutManager(requireContext())
        val rvAdapter = StudentsGroupRvAdapter()
        studentsRv.adapter = rvAdapter

        groupStudentsViewModel.studentsLiveData.observe(viewLifecycleOwner){
            data -> rvAdapter.submitList(data)
            Log.d("all student","$data")
        }

        clickedGroup.id?.let { groupStudentsViewModel.getStudentsByGroupId(it) }



        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
