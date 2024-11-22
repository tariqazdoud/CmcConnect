package com.example.cmcconnect.shared.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.CoursDto
import com.example.cmcconnect.model.EventDto
import com.example.cmcconnect.model.RessourceDto
import com.example.cmcconnect.repository.sharedRepository.EventRepository
import com.example.cmcconnect.repository.studentRepository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val eventRepository: EventRepository, private val studentRepository: StudentRepository): ViewModel() {
    private val _recentEventsLiveData = MutableLiveData<List<EventDto>?>()
    val recentEventsLiveData : LiveData<List<EventDto>?> = _recentEventsLiveData


    private val _modulesLiveDate = MutableLiveData<List<CoursDto?>>()
    val modulesLiveDate: MutableLiveData<List<CoursDto?>> = _modulesLiveDate

    private val _resourcesLiveDate = MutableLiveData<List<RessourceDto>>()
    val resourcesLiveDate: MutableLiveData<List<RessourceDto>> = _resourcesLiveDate

    private val _recentResourcesLiveData = MutableLiveData<List<RessourceDto>>()
    val recentResourcesLiveData: MutableLiveData<List<RessourceDto>> = _recentResourcesLiveData

    val _resultsLiveData = MutableLiveData<List<List<RessourceDto>>>()
    val resultsLiveData : MutableLiveData<List<List<RessourceDto>>> =  _resultsLiveData

    fun getModulesByGroupId(idGroup: Int) {
        viewModelScope.launch {
            val res = studentRepository.getRecentModulesByGroupId(idGroup)
            _modulesLiveDate.postValue(res)
        }
    }

    fun getResourcesByModuleId(idModule : List<Int>){
        viewModelScope.launch {
            val results = mutableListOf<List<RessourceDto>>()
            idModule.forEach { v->
                val res = studentRepository.getRecentResourcesByModuleId(v)
                results.add(res)
            }
            _resultsLiveData.postValue(results)

        }
    }



    fun getRecentEvents() {
        viewModelScope.launch {
            val recentEvents = eventRepository.getRecentEvents()
            _recentEventsLiveData.postValue(recentEvents)
        }
    }


}