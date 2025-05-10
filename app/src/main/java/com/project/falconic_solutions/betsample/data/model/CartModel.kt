package com.project.falconic_solutions.betsample.data.model

import com.project.falconic_solutions.betsample.data.model.response.Outcome

data class CartModel(val id: String, val homeTeam: String, val awayTeam: String, val outcome: Outcome) {}