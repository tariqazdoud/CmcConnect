package com.example.cmcconnect.admin.adminSeeRequests

import AdminSeeRequestsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.databinding.AdminFragmentSeeRequestsBinding
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminSeeRequestsFragment : Fragment(), OnStudentRequestsForAdminListener {
    private val adminSeeRequestsViewModel: AdminSeeRequestsViewModel by viewModels()
    private var _binding: AdminFragmentSeeRequestsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentSeeRequestsBinding.inflate(inflater, container, false)
        val view = binding.root

        val requestsRecyclerView: RecyclerView = binding.rvRequests
        requestsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val requestsAdapter = AdminSeeRequestsAdapter(this)
        requestsRecyclerView.adapter = requestsAdapter

        adminSeeRequestsViewModel.requestsForAdminLiveData.observe(viewLifecycleOwner) { requestWithStudents ->
            requestWithStudents?.let {
                requestsAdapter.submitList(it)
            }
        }

        adminSeeRequestsViewModel.adminReplyToRequestLiveData .observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Response Sent successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Failed to send response", Toast.LENGTH_LONG).show()
            }
        }

        adminSeeRequestsViewModel.loadStudentRequestsForAdmin(UserInInfo.id)

        return view
    }

    override fun onStudentRequestForAdminReply(
        response: String,
        admin: Int,
        student: Int,
        request: Int
    ) {
        adminSeeRequestsViewModel.adminReplyToStudent(response, admin, student, request)
    }


}