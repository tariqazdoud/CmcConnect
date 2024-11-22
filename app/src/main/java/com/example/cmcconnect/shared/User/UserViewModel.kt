package com.example.cmcconnect.shared.User

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.UserDto
import com.example.cmcconnect.model.UserInDto
import com.example.cmcconnect.repository.sharedRepository.AuthenticationRepository
import com.example.cmcconnect.repository.sharedRepository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {
    private val _userInfoLiveDate = MutableLiveData<UserInDto?>()
    val userInfoLiveDate: MutableLiveData<UserInDto?> = _userInfoLiveDate

    private val _currentUserEmail = MutableLiveData<String>()
    val currentUserEmail: LiveData<String> = _currentUserEmail

    //
//    fun getUserInfo(userEmail: String){
//        viewModelScope.launch {
//            val result = userRepository.getUserInfo(userEmail)
//            _userInfoLiveDate.postValue(result)
//        }
//    }
    fun getUserInInfo(){
        viewModelScope.launch {
            val userEmail = authenticationRepository.getCurrentUserEmail()
            val userInDto = userRepository.getUserInDto(userEmail)
            val userInfo = userInDto.user_email?.let { userInDto.id_type_user_fk?.let { it1 ->
                userRepository.getUserInfo(it,
                    it1
                )
            } }
            _userInfoLiveDate.postValue(userInfo)
        }

    }

    fun getCurrentUserEmail() {
        viewModelScope.launch {
            val fetchedResult = authenticationRepository.getCurrentUserEmail()
            _currentUserEmail.postValue(fetchedResult)
        }
    }

}