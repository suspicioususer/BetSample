package com.project.falconic_solutions.betsample.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.falconic_solutions.betsample.data.repository.ApiRepository
import com.project.falconic_solutions.betsample.ui.state.DetailScreenUIState
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
class DetailViewModel @Inject constructor(private val apiRepository: ApiRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailScreenUIState>(DetailScreenUIState.Loading)
    val uiState: StateFlow<DetailScreenUIState> = _uiState.asStateFlow()

    fun loadEvent(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailScreenUIState.Loading
            val result = apiRepository.getEventOdds(id)
            result.onSuccess { event ->
                _uiState.value = event?.let {
                    DetailScreenUIState.Success(event = event)
                } ?: run {
                    DetailScreenUIState.Empty
                }
            }
            result.onFailure { e ->
                _uiState.value = when (e) {
                    is HttpException -> DetailScreenUIState.Error("Server error: ${e.message()}")
                    is IOException -> DetailScreenUIState.Error("Network error: ${e.message}")
                    else -> DetailScreenUIState.Error("An unexpected error occurred: ${e.message}")
                }
            }
        }
    }
}