package com.example.cmcconnect.formateur.formateurGroups

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cmcconnect.adapters.teacherAdapters.GroupesRvAdapter
import com.example.cmcconnect.databinding.FormateurFragmentGroupsBinding
import com.example.cmcconnect.model.FiliereDto
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.Item
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GroupsFragment : Fragment() {
    private val groupsViewModel : GroupsViewModel by viewModels()

    private var _binding: FormateurFragmentGroupsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FormateurFragmentGroupsBinding.inflate(inflater, container, false)
        val view =  binding.root
        var groupsRv : RecyclerView = binding.GroupsRv
        groupsViewModel.groupsLiveData.observe(viewLifecycleOwner) { groups ->
            val items = prepareItems(groups)
            val adapter = GroupesRvAdapter(findNavController(),items)
            groupsRv.layoutManager = LinearLayoutManager(requireContext())
            groupsRv.adapter = adapter

            Log.d("groups", "$groups")

        }

        groupsViewModel.getCourByTeacherId(UserInInfo.id)

        return view
    }
    object ItemType {
        const val HEADER = 0
        const val GROUP = 1
    }

    fun prepareItems(groups: List<GroupeDto>): List<Item> {
        val items = mutableListOf<Item>()
        var currentFiliere: FiliereDto? = null

        for (group in groups) {
            if (group.filiere != currentFiliere) {
                currentFiliere = group.filiere
                if (currentFiliere != null) {
                    currentFiliere.name?.let { Item(ItemType.HEADER, it) }?.let { items.add(it) }
                }
            }
            group.name?.let { Item(ItemType.GROUP, it, group) }?.let { items.add(it) }
        }

        return items
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
