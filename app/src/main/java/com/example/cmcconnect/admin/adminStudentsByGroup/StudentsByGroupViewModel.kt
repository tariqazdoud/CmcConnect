package com.example.cmcconnect.admin.adminStudentsByGroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.StudentDto
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsByGroupViewModel @Inject constructor(private val adminRepository: AdminRepository) :
    ViewModel() {
    private val _studentsLiveData = MutableLiveData<List<StudentDto>>()
    val studentsLiveData : MutableLiveData<List<StudentDto>> = _studentsLiveData
    fun getStudentsByGroupId(idGroup : Int){
        viewModelScope.launch {
            val result = adminRepository.getStudentsByGroupId(idGroup)
            _studentsLiveData.postValue(result)
        }
    }
}