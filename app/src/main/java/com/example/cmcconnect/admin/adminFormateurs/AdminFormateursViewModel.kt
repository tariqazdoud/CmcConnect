package com.example.cmcconnect.admin.adminFormateurs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.TeacherDto
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminFormateursViewModel @Inject constructor(private val adminRepository: AdminRepository) :
    ViewModel() {
    private val _teachersLiveData = MutableLiveData<List<TeacherDto>>()
    val teachersLiveData : MutableLiveData<List<TeacherDto>> = _teachersLiveData

    fun getFormateursByPoleId(idPole : Int){
        viewModelScope.launch {
            val listTeacher =  mutableListOf<TeacherDto>()
            val result = adminRepository.getFormateursByPoleId(idPole)
            result.forEach {
                teacher->
                teacher.teacher?.let { listTeacher.add(it) }
            }
            _teachersLiveData.postValue(listTeacher)
        }
    }


}