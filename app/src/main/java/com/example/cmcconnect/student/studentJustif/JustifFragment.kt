package com.example.cmcconnect.student.studentJustif

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cmcconnect.databinding.FragmentJustifBinding
import com.example.cmcconnect.model.UserInInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JustifFragment : Fragment() {
    private val justifViewModel: JustifViewModel by viewModels()
    private var _binding: FragmentJustifBinding? = null
    private val binding get() = _binding!!
    private var selectedFileUri: Uri? = null

    private val filePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedFileUri = result.data?.data
            if (selectedFileUri != null) {
                binding.selectedJustifTitle.text = selectedFileUri?.let { getFileName(it) }
                Log.d("JustifFragment", "File selected: $selectedFileUri")
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
        _binding = FragmentJustifBinding.inflate(inflater, container, false)
        val view = binding.root

        val motif: EditText = binding.justifMotifEt
        val selectFile: ImageButton = binding.selectFile
        val btnSendJustif: Button = binding.sendJustifButton

        selectFile.setOnClickListener {
            openFilePicker()
        }

        btnSendJustif.setOnClickListener {
            val motifText = motif.text.toString().trim()
            if (motifText.isEmpty()) {
                Toast.makeText(requireContext(), "Motif cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedFileUri == null) {
                Toast.makeText(requireContext(), "Please select a file", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idStudent = UserInInfo.id
            justifViewModel.adminForStudent.observe(viewLifecycleOwner) { admin ->
                val adminId = admin?.id ?: return@observe
                sendJustif(motifText, selectedFileUri!!, idStudent, adminId)
            }
            justifViewModel.getStudentAdmin(idStudent)
        }

        justifViewModel.sendJustifStatus.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(requireContext(), "Justification sent successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to send justification", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }
        filePickerLauncher.launch(intent)
    }

    private fun sendJustif(motif: String, fileUri: Uri, student: Int, admin: Int) {
        justifViewModel.sendJustif(motif, fileUri, student, admin)
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

