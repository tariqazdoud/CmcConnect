package com.example.cmcconnect.shared.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.repository.sharedRepository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authenticationRepository: AuthenticationRepository): ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState.Initial)
    val signInState: StateFlow<SignInState> = _signInState

    private val _currentUserEmail = MutableLiveData<String>()
    val currentUserEmail : LiveData<String> = _currentUserEmail

    sealed class SignInState {
        object Initial : SignInState()
        object Loading : SignInState()
        data class Success(val success: Boolean) : SignInState()
        data class Error(val message: String) : SignInState()
    }

    fun onSignIn(email: String, password: String){
        viewModelScope.launch { _signInState.value = SignInState.Loading
            try {
                val success = authenticationRepository.signIn(email, password)
                _signInState.value = SignInState.Success(success)
            }catch (e:Exception){
                _signInState.value = SignInState.Error("Login Error")
            }
        }
    }

    fun getCurrentUserEmail(){
        viewModelScope.launch {
            val fetchedResult = authenticationRepository.getCurrentUserEmail()
            _currentUserEmail.postValue(fetchedResult)
        }
    }
    fun logout() {
        viewModelScope.launch {
            val result = authenticationRepository.logout()
            if (result) {
                _signInState.value = SignInState.Success(false)  // Indicates logged out successfully
            } else {
                _signInState.value = SignInState.Error("Failed to log out")
            }
        }
    }
}