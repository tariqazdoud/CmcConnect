package com.example.cmcconnect.shared.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cmcconnect.model.EventDto
import com.example.cmcconnect.repository.sharedRepository.EventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventRepository: EventRepository): ViewModel() {
    private val _allEventsLiveData = MutableLiveData<List<EventDto>?>()
    val allEventsLiveData : LiveData<List<EventDto>?> = _allEventsLiveData

    fun getAllEvents() {
        viewModelScope.launch {
            val allEvents = eventRepository.getAllEvents()
            _allEventsLiveData.postValue(allEvents)
        }
    }

}