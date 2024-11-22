package com.example.cmcconnect.student.studentJustif

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.AdminDto
import com.example.cmcconnect.model.JustifToSend
import com.example.cmcconnect.repository.studentRepository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JustifViewModel @Inject constructor(private val studentRepository: StudentRepository): ViewModel() {
    private val _sendJustifStatus = MutableLiveData<Boolean>()
    val sendJustifStatus: MutableLiveData<Boolean> = _sendJustifStatus

    private val _adminForStudent = MutableLiveData<AdminDto>()
    val adminForStudent: MutableLiveData<AdminDto> = _adminForStudent

    fun sendJustif(motif: String, file: Uri, student: Int, admin: Int) {
        viewModelScope.launch {
            val fileName = "justif_${System.currentTimeMillis()}"
            val fileUrl = studentRepository.uploadFileToBucket(file, fileName)

            if (fileUrl != null) {
                val justif = JustifToSend(
                    motif = motif,
                    file = fileUrl,
                    id_student_fk = student,
                    id_admin_fk = admin
                )
                val success = studentRepository.sendJustif(justif)
                _sendJustifStatus.postValue(success)
            } else {
                _sendJustifStatus.postValue(false)
            }
        }
    }

    fun getStudentAdmin(idStudent: Int) {
        viewModelScope.launch {
            val admin = studentRepository.getStudentAdmin(idStudent)
            _adminForStudent.postValue(admin)
        }
    }
}