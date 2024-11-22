package com.example.cmcconnect.formateur.formateurSeeRequests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.adapters.teacherAdapters.SeeRequestsAdapter
import com.example.cmcconnect.databinding.FormateurFragmentSeeRequestsBinding
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeeRequestsFragment : Fragment(), OnStudentRequestReplyListener {
    private val seeRequestsViewModel: SeeRequestsViewModel by viewModels()
    private var _binding: FormateurFragmentSeeRequestsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FormateurFragmentSeeRequestsBinding.inflate(inflater, container, false)
        val view = binding.root

        val requestsRecyclerView: RecyclerView = binding.rvRequests
        requestsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val requestsAdapter = SeeRequestsAdapter(this)
        requestsRecyclerView.adapter = requestsAdapter

        seeRequestsViewModel.requestsForTeacherLiveData.observe(viewLifecycleOwner) { requestWithStudents ->
            requestWithStudents?.let {
                requestsAdapter.submitList(it)
            }
        }

        seeRequestsViewModel.loadRequestsForTeacher(UserInInfo.id)

        seeRequestsViewModel.replyToRequestLiveData.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Response Sent successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Failed to send response", Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStudentRequestReply(response: String, teacher: Int, student: Int, request: Int) {
        seeRequestsViewModel.teacherReplyToStudent(response, teacher, student, request)
    }
}


