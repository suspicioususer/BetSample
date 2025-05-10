package com.project.falconic_solutions.betsample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.falconic_solutions.betsample.data.model.response.Event
import com.project.falconic_solutions.betsample.data.repository.ApiRepository
import com.project.falconic_solutions.betsample.ui.state.MainScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private var eventList: List<Event> = emptyList()

    private val _uiState = MutableStateFlow<MainScreenUIState>(MainScreenUIState.Initial)
    val uiState: StateFlow<MainScreenUIState> = _uiState.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _uiState.value = MainScreenUIState.Loading
            val result = apiRepository.getEvents()
            result.onSuccess { events ->
                _uiState.value = if (events.isEmpty()) {
                    MainScreenUIState.Empty
                } else {
                    eventList = events
                    MainScreenUIState.Success(events = events)
                }
            }
            result.onFailure { e ->
                _uiState.value = when (e) {
                    is HttpException -> MainScreenUIState.Error("Server error: ${e.message()}")
                    is IOException -> MainScreenUIState.Error("Network error: ${e.message}")
                    else -> MainScreenUIState.Error("An unexpected error occurred: ${e.message}")
                }
            }
        }
    }

    fun clearSearchText() {
        _uiState.value = (uiState.value as MainScreenUIState.Success).copy(searchText = "", events = eventList)
    }

    fun filterEvents(searchText: String) {
        val filteredEvents = eventList.filter { event ->
            event.homeTeam.contains(searchText, ignoreCase = true) || event.awayTeam.contains(searchText, ignoreCase = true)
        }

        _uiState.value = (uiState.value as MainScreenUIState.Success).copy(searchText = searchText, events = filteredEvents)
    }

    fun findEventById(id: String): Event? {
        return eventList.find { it.id == id }
    }
}