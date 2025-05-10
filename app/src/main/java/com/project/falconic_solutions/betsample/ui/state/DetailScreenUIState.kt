package com.project.falconic_solutions.betsample.ui.state

import com.project.falconic_solutions.betsample.data.model.response.Event

sealed class DetailScreenUIState {
    data object Loading : DetailScreenUIState()
    data class Success(val event: Event) : DetailScreenUIState()
    data object Empty : DetailScreenUIState()
    data class Error(val message: String) : DetailScreenUIState()
}