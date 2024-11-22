package com.example.cmcconnect.admin.adminFiliere

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.FiliereDto
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiliereViewModel @Inject constructor(private val adminRepository: AdminRepository) :
    ViewModel() {
    private val _filieresLiveData = MutableLiveData<List<FiliereDto>>()
    val filieresLiveData : MutableLiveData<List<FiliereDto>> = _filieresLiveData

    private val _groupsLiveData = MutableLiveData<List<GroupeDto>>()
    val groupsLiveData: MutableLiveData<List<GroupeDto>> = _groupsLiveData
    fun getFilieresByPoleId(idPole:Int){
        viewModelScope.launch {
            val listFilieres = adminRepository.getFilieresByPoleId(idPole)
            _filieresLiveData.postValue(listFilieres)
        }
    }
    fun getGroupsByFiliereId(idFiliere: Int){
        viewModelScope.launch {
            val listGroups = adminRepository.getGroupsByFiliereId(idFiliere)
            _groupsLiveData.postValue(listGroups)
        }
    }
}