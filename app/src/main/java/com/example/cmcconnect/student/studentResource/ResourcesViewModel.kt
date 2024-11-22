package com.example.cmcconnect.student.studentResource

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.CoursDto
import com.example.cmcconnect.model.ModuleDto
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.repository.studentRepository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResourcesViewModel @Inject constructor(private val studentRepository: StudentRepository) :
    ViewModel() {

    private val _modulesLiveDate = MutableLiveData<List<CoursDto?>>()
    val modulesLiveDate: MutableLiveData<List<CoursDto?>> = _modulesLiveDate

    private val _resourcesLiveDate = MutableLiveData<List<RessourceDto>>()
    val resourcesLiveDate: MutableLiveData<List<RessourceDto>> = _resourcesLiveDate

    private val _recentResourcesLiveData = MutableLiveData<List<RessourceDto>>()
    val recentResourcesLiveData: MutableLiveData<List<RessourceDto>> = _recentResourcesLiveData
    fun getModulesByGroupId(idGroup: Int) {
        viewModelScope.launch {
            val res = studentRepository.getModulesByGroupId(idGroup)
            _modulesLiveDate.postValue(res)
        }
    }

    fun getResourcesByModuleId(idModule : Int){
        viewModelScope.launch {
            val res = studentRepository.getResourcesByModuleId(idModule)
            _resourcesLiveDate.postValue(res)
        }
    }


}