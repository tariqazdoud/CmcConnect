package com.example.cmcconnect.admin.adminSeeRequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.RequestWithStudent
import com.example.cmcconnect.model.StudentRequestForAdminReplyToPost
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminSeeRequestsViewModel @Inject constructor(private val adminRepository: AdminRepository): ViewModel() {
    private val _requestsForAdminLiveData = MutableLiveData<List<RequestWithStudent>>()
    val requestsForAdminLiveData: LiveData<List<RequestWithStudent>> get() = _requestsForAdminLiveData

    private val _adminReplyToRequestLiveData = MutableLiveData<Boolean>()
    val adminReplyToRequestLiveData: MutableLiveData<Boolean> = _adminReplyToRequestLiveData

    fun loadStudentRequestsForAdmin(idAdmin: Int) {
        viewModelScope.launch {
            val result = adminRepository.loadStudentRequestsForAdmin(idAdmin)
            _requestsForAdminLiveData.postValue(result)
        }
    }

    fun adminReplyToStudent(response: String, admin: Int, student: Int, request: Int) {
        viewModelScope.launch {
            val reply = StudentRequestForAdminReplyToPost (
                response = response,
                id_admin_fk = admin,
                id_student_fk = student,
                id_request_fk = request
            )
            val result = adminRepository.adminReplyToStudent(reply)
            _adminReplyToRequestLiveData.postValue(result)
        }
    }
}