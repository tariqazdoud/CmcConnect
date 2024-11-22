package com.example.cmcconnect.admin.adminJustifs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.JustifDto
import com.example.cmcconnect.model.JustifWithStudent
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminJustifsViewModel @Inject constructor(private val adminRepository: AdminRepository): ViewModel() {
    private val _justifsLiveData = MutableLiveData<List<JustifWithStudent>>()
    val justifsLiveData: MutableLiveData<List<JustifWithStudent>> = _justifsLiveData

    fun loadJustifsForAdmin(idAdmin: Int) {
        viewModelScope.launch {
            val justifs = adminRepository.loadJustifsForAdmin(idAdmin)
            _justifsLiveData.postValue(justifs)
        }
    }
}