package com.example.cmcconnect.formateur.formateurPostResources

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.ResourceToPost
import com.example.cmcconnect.repository.teacherRepository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import javax.inject.Inject

@HiltViewModel
class PostResourcesViewModel @Inject constructor(private val teacherRepository: TeacherRepository): ViewModel() {
    private val _groupsOfTeacherLiveData = MutableLiveData<List<GroupeDto>>()
    val groupsOfTeacherLiveData: MutableLiveData<List<GroupeDto>> = _groupsOfTeacherLiveData

    private val _modulesOfGroupLiveData = MutableLiveData<List<ModuleDto>>()
    val modulesOfGroupLiveData: MutableLiveData<List<ModuleDto>> = _modulesOfGroupLiveData

    private val _postResourceStatus = MutableLiveData<Boolean>()
    val postResourceStatus: MutableLiveData<Boolean> = _postResourceStatus

    fun loadGroupsOfTeacher(idTeacher: Int) {
        viewModelScope.launch {
            val groups = mutableListOf<GroupeDto>()
            val result = teacherRepository.loadGroupsOfTeacher(idTeacher)
            result.forEach {
                res -> res.groupe?.let { groups.add(it) }
            }
            _groupsOfTeacherLiveData.postValue(groups)
        }
    }

    fun loadModulesOfGroup(idTeacher: Int, idGroup: Int) {
        viewModelScope.launch {
            val modules = mutableListOf<ModuleDto>()
            val result = teacherRepository.loadModulesOfGroup(idTeacher, idGroup)
            result.forEach {
                res -> res.module?.let { modules.add(it) }
            }
            _modulesOfGroupLiveData.postValue(modules)
        }
    }

    fun postResource(title: String, file: Uri, type: String, pubDate: LocalDate, module: Int, teacher: Int) {
        viewModelScope.launch {
            val fileName = "ressource_${System.currentTimeMillis()}"
            val fileUrl = teacherRepository.uploadResourceToBucket(file, fileName)

            if (fileUrl != null) {
                val resource = ResourceToPost(
                    title = title,
                    file = fileUrl,
                    type = type,
                    pubDate = pubDate,
                    id_module_fk = module,
                    id_teacher_fk = teacher
                )
                val success = teacherRepository.postResource(resource)
                _postResourceStatus.postValue(success)
            } else {
                _postResourceStatus.postValue(false)
            }
        }
    }
}