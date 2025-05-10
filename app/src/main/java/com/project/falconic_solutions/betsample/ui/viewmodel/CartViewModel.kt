package com.project.falconic_solutions.betsample.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.project.falconic_solutions.betsample.data.model.CartModel
import com.project.falconic_solutions.betsample.data.model.response.Event
import com.project.falconic_solutions.betsample.data.model.response.Outcome
import com.project.falconic_solutions.betsample.data.model.response.toCartModel
import com.project.falconic_solutions.betsample.ui.state.CartScreenUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {

    private var betMap = mutableMapOf<String, CartModel>()

    private val _uiState = MutableStateFlow<CartScreenUIState>(CartScreenUIState.Empty)
    val uiState: StateFlow<CartScreenUIState> = _uiState.asStateFlow()

    fun clearBet() {
        _uiState.value = CartScreenUIState.Loading
        betMap.clear()
        _uiState.value = CartScreenUIState.Empty
    }

    fun addBet(event: Event, bookmakerKey: String, outcome: Outcome): Boolean {
        _uiState.value = CartScreenUIState.Loading
        val bet = event.toCartModel(bookmakerKey, outcome)
        val key = bet.id
        var result = false
        if (!betMap.contains(key)) {
            betMap[key] = bet
            result = true
        }
        val totalAmount = betMap.values.sumOf { it.outcome.price }
        _uiState.value = CartScreenUIState.Success(bets = betMap, totalAmount = totalAmount)
        return result
    }

    fun removeBet(id: String) {
        _uiState.value = CartScreenUIState.Loading
        betMap.remove(id)
        if (betMap.isNotEmpty()) {
            val totalAmount = betMap.values.sumOf { it.outcome.price }
            _uiState.value = CartScreenUIState.Success(bets = betMap, totalAmount = totalAmount)
        } else {
            _uiState.value = CartScreenUIState.Empty
        }
    }

}