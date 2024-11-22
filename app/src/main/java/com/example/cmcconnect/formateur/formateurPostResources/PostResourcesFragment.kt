package com.example.cmcconnect.formateur.formateurPostResources

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cmcconnect.databinding.FormateurFragmentPostResourcesBinding
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@AndroidEntryPoint
class PostResourcesFragment : Fragment() {
    private val postResourceViewModel: PostResourcesViewModel by viewModels()
    private var _binding: FormateurFragmentPostResourcesBinding? = null
    private val binding get() = _binding!!
    private var selectedFileUri: Uri? = null
    private var selectedFileType: String? = null
    private var resourceTitle: String? = null

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedFileUri = result.data?.data
            selectedFileType = result.data?.data?.let { getFileType(it) }
            resourceTitle = selectedFileUri?.let { getFileName(it) }
            if (selectedFileUri != null) {
                binding.selectedFileTitle.text = resourceTitle
                Log.d("PostResourcesFragment", "File selected: $selectedFileUri")
                Toast.makeText(requireContext(), "File selected: $selectedFileUri", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "File selection failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FormateurFragmentPostResourcesBinding.inflate(inflater, container, false)
        val view = binding.root

        val groupsSpinner: Spinner = binding.spinnerGroups
        val modulesSpinner: Spinner = binding.spinnerModules
        val selectFileButton: ImageButton = binding.selectFile
        val postResourceButton: Button = binding.postRessourceButton

        selectFileButton.setOnClickListener {
            openFilePicker()
        }

        postResourceButton.setOnClickListener {
            if (resourceTitle.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "File title cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedFileUri == null || selectedFileType == null) {
                Toast.makeText(requireContext(), "Please select a file", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idTeacher = UserInInfo.id
            val selectedModule = modulesSpinner.selectedItem as? ModuleDto
            Log.d("PostResourcesFragment", "Selected module: $selectedModule")
            val moduleId = selectedModule?.id
            Log.d("PostResourcesFragment", "Selected module ID: $moduleId")

            if (moduleId != null) {
                val pubDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
                postResourceViewModel.postResource(resourceTitle!!, selectedFileUri!!, selectedFileType!!, pubDate, moduleId, idTeacher)
            } else {
                Toast.makeText(requireContext(), "Please select a module", Toast.LENGTH_SHORT).show()
            }
        }

        val idTeacher = UserInInfo.id

        postResourceViewModel.groupsOfTeacherLiveData.observe(viewLifecycleOwner) { groups ->
            val groupsNames = groups.map { it.name }
            val groupsSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, groupsNames)
            groupsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            groupsSpinner.adapter = groupsSpinnerAdapter

            groupsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedGroupName = parent.getItemAtPosition(position) as String
                    postResourceViewModel.groupsOfTeacherLiveData.value?.let { groups ->
                        val selectedGroup = groups.find { it.name == selectedGroupName }
                        selectedGroup?.id?.let { groupId ->
                            Log.d("Groups Spinner", "Selected group ID: $groupId")
                            postResourceViewModel.loadModulesOfGroup(idTeacher, groupId)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

        postResourceViewModel.modulesOfGroupLiveData.observe(viewLifecycleOwner) { modules ->
            val modulesSpinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, modules)
            modulesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerModules.adapter = modulesSpinnerAdapter
        }

        postResourceViewModel.postResourceStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Resource posted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to post resource", Toast.LENGTH_SHORT).show()
            }
        }

        postResourceViewModel.loadGroupsOfTeacher(idTeacher)

        return view
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        filePickerLauncher.launch(intent)
    }

    private fun getFileType(uri: Uri): String {
        val contentResolver = requireContext().contentResolver
        val mimeType = contentResolver.getType(uri)
        return when {
            mimeType?.startsWith("image") == true -> "image"
            mimeType?.startsWith("video") == true -> "video"
            mimeType == "application/pdf" -> "pdf"
            else -> "unknown"
        }
    }

    private fun getFileName(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        return if (cursor != null && cursor.moveToFirst()) {
            val displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val displayName = cursor.getString(displayNameIndex)
            cursor.close()
            displayName
        } else {
            "Unknown"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
