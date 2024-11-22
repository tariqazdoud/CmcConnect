package com.example.cmcconnect.admin.adminJustifs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.R
import com.example.cmcconnect.adapters.adminAdapters.SeeJustifsAdapter
import com.example.cmcconnect.databinding.AdminFragmentJustifsBinding
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminJustifsFragment : Fragment() {
    private val adminJustifsViewModel: AdminJustifsViewModel by viewModels()
    private var _binding: AdminFragmentJustifsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentJustifsBinding.inflate(inflater, container, false)
        val view = binding.root

        val justifsRecyclerView: RecyclerView = binding.justifsRv
        justifsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val justifsAdapter = SeeJustifsAdapter()
        justifsRecyclerView.adapter = justifsAdapter

        adminJustifsViewModel.justifsLiveData.observe(viewLifecycleOwner) { justif ->
            justif?.let {
                justifsAdapter.submitList(it)
            }
        }

        adminJustifsViewModel.loadJustifsForAdmin(UserInInfo.id)

        return view
    }

}