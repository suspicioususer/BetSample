package com.project.falconic_solutions.betsample.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.project.falconic_solutions.betsample.R
import com.project.falconic_solutions.betsample.data.model.response.Event
import com.project.falconic_solutions.betsample.data.model.response.Outcome
import com.project.falconic_solutions.betsample.ui.state.DetailScreenUIState

@Composable
fun EventDetailScreen(
    uiState: DetailScreenUIState, id: String, getEventOddsAction: (String) -> Unit = { }, addAction: (Event, String, Outcome) -> Unit = { _, _, _ -> }
) {
    var dataFetched by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = dataFetched) {
        if (!dataFetched) {
            getEventOddsAction.invoke(id)
            dataFetched = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is DetailScreenUIState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is DetailScreenUIState.Success -> {
                DetailList(
                    uiState = uiState, addAction = addAction
                )
            }

            is DetailScreenUIState.Empty -> {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .align(Alignment.CenterHorizontally), text = stringResource(R.string.main_list_no_detail_found)
                )
            }

            is DetailScreenUIState.Error -> {
                Text(text = uiState.message)
            }
        }
    }
}

@Composable
fun DetailList(
    uiState: DetailScreenUIState.Success, addAction: (Event, String, Outcome) -> Unit = { _, _, _ -> }
) {
    val event = uiState.event
    LazyColumn(modifier = Modifier) {
        itemsIndexed(event.bookmakers, key = ({ index, bookmaker -> bookmaker.key })) { index, bookmaker ->
            OddsComposable(
                bookmakerTitle = bookmaker.title, outcomes = bookmaker.markets.first().outcomes
            ) { outcome ->
                addAction.invoke(event, bookmaker.key, outcome)
            }
        }
    }
}