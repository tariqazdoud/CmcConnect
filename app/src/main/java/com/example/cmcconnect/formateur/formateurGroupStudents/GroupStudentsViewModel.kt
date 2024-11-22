package com.example.cmcconnect.formateur.formateurGroupStudents

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.StudentDto
import com.example.cmcconnect.repository.teacherRepository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupStudentsViewModel @Inject constructor(private val teacherRepository: TeacherRepository) :
    ViewModel() {
    private val _studentsLiveData = MutableLiveData<List<StudentDto>>()
    val studentsLiveData : MutableLiveData<List<StudentDto>> = _studentsLiveData

    fun getStudentsByGroupId(idGroup : Int){
        viewModelScope.launch {
            val result = teacherRepository.getStudentsByGroupId(idGroup)
            _studentsLiveData.postValue(result)
        }
    }
}