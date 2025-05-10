package com.project.falconic_solutions.betsample.ui.state

import com.project.falconic_solutions.betsample.data.model.response.Event

sealed class MainScreenUIState {
    data object Initial : MainScreenUIState()
    data object Loading : MainScreenUIState()
    data class Success(val searchText: String = "", val events: List<Event>) : MainScreenUIState()
    data object Empty : MainScreenUIState()
    data class Error(val message: String) : MainScreenUIState()
}