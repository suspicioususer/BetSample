package com.project.falconic_solutions.betsample.ui.state

import com.project.falconic_solutions.betsample.data.model.CartModel

sealed class CartScreenUIState {
    data object Loading : CartScreenUIState()
    data class Success(val bets: Map<String, CartModel>, val totalAmount: Double) : CartScreenUIState()
    data object Empty : CartScreenUIState()
    data class Error(val message: String) : CartScreenUIState()
}