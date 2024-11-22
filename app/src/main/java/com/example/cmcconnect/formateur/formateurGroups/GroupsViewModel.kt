package com.example.cmcconnect.formateur.formateurGroups

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.repository.teacherRepository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(private val teacherRepository: TeacherRepository) :
    ViewModel() {

    private val _groupsLiveData = MutableLiveData<List<GroupeDto>>()
    val groupsLiveData: MutableLiveData<List<GroupeDto>> = _groupsLiveData

    fun getCourByTeacherId(idTeacher: Int) {
        viewModelScope.launch {
            val listGroups = mutableListOf<GroupeDto>()
            val result = teacherRepository.getCourByTeacherId(idTeacher)
            result.forEach { res -> res.groupe?.let { listGroups.add(it) } }
            _groupsLiveData.postValue(listGroups)
        }
    }
}