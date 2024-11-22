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
import com.example.cmcconnect.databinding.AdminFragmentAdminAddGroupBinding
import com.example.cmcconnect.model.FiliereDto
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AdminAddGroupFragment : Fragment() {
    private val addGroupViewModel: AddGroupViewModel by viewModels()

    private var _binding: AdminFragmentAdminAddGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AdminFragmentAdminAddGroupBinding.inflate(inflater, container, false)
        val view = binding.root
        val filiereEt: EditText = binding.groupFiliereTv
        val yearEt: EditText = binding.groupYearTv
        val groupNameTv: EditText = binding.groupNameTv
        val postResourceButton = binding.postRessourceButton
        val selectedFiliere = arguments?.getSerializable("selectedFiliere") as FiliereDto

        filiereEt.setText(selectedFiliere.name)
        filiereEt.isFocusable = false
        filiereEt.isFocusableInTouchMode = false
        filiereEt.isClickable = false

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val nextYear = currentYear + 1
        val yearString = "$currentYear/$nextYear"





        yearEt.setText(yearString)
        yearEt.isFocusable = false
        yearEt.isFocusableInTouchMode = false
        yearEt.isClickable = false

        postResourceButton.setOnClickListener {
            val groupName = groupNameTv.text.toString()
            if (yearString == "2024/2025") {
                selectedFiliere.id?.let { addGroupViewModel.addGroup(groupName, 1, it) }
            }
            addGroupViewModel.addGroupStatus.observe(viewLifecycleOwner) { statu ->
                if (statu == true) {
                    Toast.makeText(requireContext(),"successfully added group",Toast.LENGTH_SHORT).show()
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
