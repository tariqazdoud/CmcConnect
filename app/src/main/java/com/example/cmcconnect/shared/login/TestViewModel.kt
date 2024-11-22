package com.example.cmcconnect.shared.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.YearDto
import com.example.cmcconnect.repository.testRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val testRepo: testRepo):ViewModel(){
    private val _test = MutableLiveData<YearDto?>()
    val test : LiveData<YearDto?> = _test

    fun  getYear(){
        viewModelScope.launch {
            val result = testRepo.getYear()
            _test.postValue(result)
        }
    }
}