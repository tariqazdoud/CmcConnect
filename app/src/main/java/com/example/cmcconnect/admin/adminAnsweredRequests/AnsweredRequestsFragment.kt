package com.example.cmcconnect.admin.adminAnsweredRequests

import AdminSeeRequestsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.adapters.adminAdapters.AdminSeeAnsweredRequestsAdapter
import com.example.cmcconnect.databinding.AdminFragmentAnsweredRequestsBinding
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnsweredRequestsFragment : Fragment() {
    private val answeredRequestsViewModel: AnsweredRequestsViewModel by viewModels()
    private var _binding: AdminFragmentAnsweredRequestsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentAnsweredRequestsBinding.inflate(inflater, container, false)
        val view = binding.root

        val answeredRequestsRecyclerView: RecyclerView = binding.rvRequests
        answeredRequestsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val answeredRequestsAdapter = AdminSeeAnsweredRequestsAdapter()
        answeredRequestsRecyclerView.adapter = answeredRequestsAdapter

        answeredRequestsViewModel.answeredRequestsLiveData.observe(viewLifecycleOwner) {
            answeredRequest -> answeredRequest.let {
            answeredRequestsAdapter.submitList(answeredRequest)
            }
        }

        answeredRequestsViewModel.getAnsweredRequests(UserInInfo.id)

        return view
    }


}