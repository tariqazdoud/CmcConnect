package com.example.cmcconnect.admin.adminAddGroup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.GroupToPost
import com.example.cmcconnect.model.GroupeDto
import com.example.cmcconnect.model.StudentToSend
import com.example.cmcconnect.repository.adminRepository.AdminRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(private val adminRepository: AdminRepository) :
    ViewModel() {
    private val _addGroupStatus = MutableLiveData<Boolean>()
    val addGroupStatus: MutableLiveData<Boolean> = _addGroupStatus

    private val _addStudentStatus = MutableLiveData<Boolean>()
    val addStudentStatus: MutableLiveData<Boolean> = _addStudentStatus


    fun addGroup(name: String, id_year_fk: Int, id_filiere_fk: Int) {
        viewModelScope.launch {
            val group = GroupToPost(
                name = name,
                id_year_fk = id_year_fk,
                id_filiere_fk = id_filiere_fk
            )
            val success = adminRepository.addGroup(group)
            _addGroupStatus.postValue(success)
        }
    }

    fun addStudent(
        name: String,
        email: String,
        phone: String,
        image: String,
        id_groupe_fk: Int,
        id_type_user_fk: Int
    ) {
        viewModelScope.launch {
            val student = StudentToSend(
                name = name,
                email = email,
                phone = phone,
                image = image,
                id_groupe_fk = id_groupe_fk,
                id_type_user_fk = id_type_user_fk
            )
            val success = adminRepository.addStudent(student)
            _addStudentStatus.postValue(success)
        }
    }
}