package com.example.cmcconnect.student.studentRequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.AdminDto
import com.example.cmcconnect.model.RequestToSend
import com.example.cmcconnect.model.RequestTypeDto
import com.example.cmcconnect.model.TeacherDto
import com.example.cmcconnect.repository.studentRepository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(private val studentRepository: StudentRepository): ViewModel() {
    private val _allRequestsLiveData = MutableLiveData<List<RequestTypeDto>>()
    val allRequestsLiveData: MutableLiveData<List<RequestTypeDto>> = _allRequestsLiveData

    private val _teachersForStudent = MutableLiveData<List<TeacherDto>>()
    val teachersForStudent: MutableLiveData<List<TeacherDto>> = _teachersForStudent

    private val _adminForStudent = MutableLiveData<AdminDto>()
    val adminForStudent: MutableLiveData<AdminDto> = _adminForStudent

    private val _sendRequestStatus = MutableLiveData<Boolean>()
    val sendRequestStatus: MutableLiveData<Boolean> = _sendRequestStatus

    fun loadRequests() {
        viewModelScope.launch {
            val requests = studentRepository.loadRequests()
            _allRequestsLiveData.postValue(requests)
        }
    }

    fun loadTeachersForStudent(idStudent: Int) {
        viewModelScope.launch {
            val teachers = studentRepository.loadTeachersForStudent(idStudent)
            _teachersForStudent.postValue(teachers)
        }
    }

    fun getStudentAdmin(idStudent: Int) {
        viewModelScope.launch {
            val admin = studentRepository.getStudentAdmin(idStudent)
            _adminForStudent.postValue(admin)
        }
    }

    fun sendRequest(motif: String, student: Int, requestType: Int, teacher: Int?, admin: Int?) {
        viewModelScope.launch {
            val request = RequestToSend(
                motif = motif,
                id_student_fk = student,
                id_type_request_fk = requestType,
                id_teacher_fk = teacher,
                id_admin_fk = admin,
                answered = false
            )
            val success = studentRepository.sendRequest(request)
            _sendRequestStatus.postValue(success)
        }
    }

}
