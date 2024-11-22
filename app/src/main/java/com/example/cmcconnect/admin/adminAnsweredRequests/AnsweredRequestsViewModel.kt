package com.example.cmcconnect.admin.adminAnsweredRequests

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.AnsweredRequestsWithRequestDetails
import com.example.cmcconnect.model.RequestDto
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnsweredRequestsViewModel @Inject constructor(private val adminRepository: AdminRepository): ViewModel() {
    private val _answeredRequestsLiveData = MutableLiveData<List<AnsweredRequestsWithRequestDetails>>()
    val answeredRequestsLiveData: MutableLiveData<List<AnsweredRequestsWithRequestDetails>> = _answeredRequestsLiveData

    fun getAnsweredRequests(idAdmin: Int) {
        viewModelScope.launch {
            val result = adminRepository.getAnsweredRequests(idAdmin)
            _answeredRequestsLiveData.postValue(result)
        }
    }
}