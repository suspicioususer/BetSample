package com.project.falconic_solutions.betsample.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.project.falconic_solutions.betsample.R
import com.project.falconic_solutions.betsample.ui.state.MainScreenUIState

@Composable
fun EventScreen(
    uiState: MainScreenUIState,
    onSearch: (String) -> Unit = {},
    clearAction: () -> Unit = {},
    detailAction: (String) -> Unit = { }
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is MainScreenUIState.Initial -> {}
            is MainScreenUIState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is MainScreenUIState.Success -> {
                EventList(
                    uiState = uiState, onSearch = onSearch, clearAction = clearAction, detailAction = detailAction
                )
            }

            is MainScreenUIState.Empty -> {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .align(Alignment.CenterHorizontally), text = stringResource(R.string.main_list_no_event_found)
                )
            }

            is MainScreenUIState.Error -> {
                Text(text = uiState.message)
            }
        }
    }
}

@Composable
fun EventList(
    uiState: MainScreenUIState.Success,
    onSearch: (String) -> Unit = {},
    clearAction: () -> Unit = {},
    detailAction: (String) -> Unit = { }
) {
    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(horizontal = 8.dp),
        leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
        trailingIcon = {
            Icon(modifier = Modifier.clickable {
                clearAction.invoke()
            }, imageVector = Icons.Filled.Delete, contentDescription = null)
        },
        placeholder = { Text(text = stringResource(R.string.main_list_search)) },
        shape = RoundedCornerShape(24.dp),
        singleLine = true,
        value = uiState.searchText,
        onValueChange = {
            if (it.isNotBlank()) {
                onSearch.invoke(it)
            }
        })
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(modifier = Modifier) {
        itemsIndexed(uiState.events, key = ({ index, event -> event.id })) { index, event ->
            EventRow(event = event) { e ->
                detailAction.invoke(e.id)
            }
        }
    }
}