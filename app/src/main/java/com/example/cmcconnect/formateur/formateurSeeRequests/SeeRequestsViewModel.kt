package com.example.cmcconnect.formateur.formateurSeeRequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.RequestDto
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.StudentDto
import com.example.cmcconnect.model.StudentRequestReplyToPost
import com.example.cmcconnect.repository.teacherRepository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeRequestsViewModel @Inject constructor(private val teacherRepository: TeacherRepository) : ViewModel() {
    private val _requestsForTeacherLiveData = MutableLiveData<List<RequestWithStudent>>()
    val requestsForTeacherLiveData: LiveData<List<RequestWithStudent>> get() = _requestsForTeacherLiveData

    private val _replyToRequestLiveData = MutableLiveData<Boolean>()
    val replyToRequestLiveData: MutableLiveData<Boolean> = _replyToRequestLiveData

    fun loadRequestsForTeacher(idTeacher: Int) {
        viewModelScope.launch {
            val result = teacherRepository.loadStudentRequestsForTeacher(idTeacher)
            _requestsForTeacherLiveData.postValue(result)
        }
    }

    fun teacherReplyToStudent(response: String, teacher: Int, student: Int, request: Int) {
        viewModelScope.launch {
            val reply = StudentRequestReplyToPost(
                response = response,
                id_teacher_fk = teacher,
                id_student_fk = student,
                id_request_fk = request
            )
            val result = teacherRepository.teacherReplyToStudent(reply)
            _replyToRequestLiveData.postValue(result)
        }
    }
}


