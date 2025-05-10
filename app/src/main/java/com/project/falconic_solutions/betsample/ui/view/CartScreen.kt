package com.project.falconic_solutions.betsample.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.falconic_solutions.betsample.R
import com.project.falconic_solutions.betsample.data.extension.formatAmount
import com.project.falconic_solutions.betsample.ui.state.CartScreenUIState

@Composable
fun CartScreen(
    uiState: CartScreenUIState, clearAction: () -> Unit = {}, removeAction: (String) -> Unit = { }
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is CartScreenUIState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CartScreenUIState.Success -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                        text = stringResource(R.string.bottom_navigation_bar_cart),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    HorizontalDivider(Modifier.fillMaxWidth().padding(vertical = 2.dp), thickness = 2.dp)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        itemsIndexed(uiState.bets.entries.toList(), key = { _, entry -> entry.value.id }) { index, entry ->
                            val model = entry.value
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(end = 4.dp),
                                    text = "${index + 1}.",
                                    fontWeight = FontWeight.Bold
                                )
                                Column(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .wrapContentHeight()
                                        .weight(1f, true),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        text = "${model.homeTeam} - ${model.awayTeam}",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        text = model.outcome.name,
                                        fontStyle = FontStyle.Italic
                                    )
                                }
                                Text(text = model.outcome.price.formatAmount(), fontWeight = FontWeight.Bold)
                                Icon(
                                    imageVector = Icons.Outlined.RemoveCircleOutline,
                                    contentDescription = "Clear",
                                    modifier = Modifier.clickable {
                                        removeAction(model.id)
                                    },
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp), thickness = 2.dp
                            )
                        }
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(horizontal = 8.dp, vertical = 16.dp)
                                    .clickable {
                                        clearAction()
                                    },
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .width(0.dp)
                                        .wrapContentHeight()
                                        .weight(1f, true),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.DeleteOutline,
                                        contentDescription = "Clear",
                                        tint = MaterialTheme.colorScheme.tertiary
                                    )
                                    Text(
                                        text = stringResource(R.string.cart_list_clear),
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                }
                                Text(
                                    modifier = Modifier.wrapContentSize(),
                                    text = stringResource(
                                        R.string.cart_list_total_amount,
                                        uiState.totalAmount.formatAmount()
                                    ),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.tertiary
                                )
                            }
                        }
                    }
                }
            }

            is CartScreenUIState.Empty -> {
                Text(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight(Alignment.CenterVertically)
                        .align(Alignment.CenterHorizontally), text = stringResource(R.string.cart_list_no_bet_found)
                )
            }

            is CartScreenUIState.Error -> {
                Text(text = uiState.message)
            }
        }
    }

}