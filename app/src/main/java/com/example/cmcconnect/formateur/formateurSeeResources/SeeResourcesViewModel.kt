package com.example.cmcconnect.formateur.formateurSeeResources

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.CoursDto
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.repository.teacherRepository.TeacherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SeeResourcesViewModel @Inject constructor(private val teacherRepository: TeacherRepository) :
    ViewModel() {
    private val _groupsLiveData = MutableLiveData<List<GroupeDto>>()
    val groupsLiveData: MutableLiveData<List<GroupeDto>> = _groupsLiveData

    private val _modulesLiveData = MutableLiveData<List<ModuleDto>>()
    val modulesLiveData: MutableLiveData<List<ModuleDto>> = _modulesLiveData

    private val _resourcesLiveDate = MutableLiveData<List<RessourceDto>>()
    val resourcesLiveDate: MutableLiveData<List<RessourceDto>> = _resourcesLiveDate
        fun getCourByTeacherId(idTeacher : Int){
            viewModelScope.launch {
                val listGroups =  mutableListOf<GroupeDto>()
                val result = teacherRepository.getCourByTeacherId(idTeacher)
                result.forEach { res-> res.groupe?.let { listGroups.add(it) } }
                _groupsLiveData.postValue(listGroups)
            }
        }
    fun getModuleByTeacherAndGroupId(idTeacher : Int,idGroup : Int){
        viewModelScope.launch {
            val listModules =  mutableListOf<ModuleDto>()
            val result = teacherRepository.getModuleByTeacherAndGroupId(idTeacher,idGroup)
            result.forEach {
                res->
                res.module?.let { listModules.add(it) }
            }
            _modulesLiveData.postValue(listModules)
        }
    }
    fun getResourceTeacherAndModuleId(idTeacher: Int, idModule: Int){
        viewModelScope.launch {
            val res = teacherRepository.getResourceTeacherAndModuleId(idTeacher,idModule)
            _resourcesLiveDate.postValue(res)
        }
    }
}