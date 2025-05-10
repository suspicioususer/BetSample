package com.project.falconic_solutions.betsample.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LogoDev
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.project.falconic_solutions.betsample.R
import com.project.falconic_solutions.betsample.data.extension.formatAmount
import com.project.falconic_solutions.betsample.data.model.response.Outcome

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun OddsComposable(
    bookmakerTitle: String, outcomes: List<Outcome>, onOutcomeSelected: (Outcome) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.tertiary)
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .width(0.dp)
                    .wrapContentHeight()
                    .weight(1f, true),
                text = bookmakerTitle,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onTertiary,
                fontSize = 14.sp
            )
            val placeholder = placeholder(R.drawable.ic_launcher_foreground)
            GlideImage(
                modifier = Modifier.size(32.dp),
                model = "https://cdn-icons-png.flaticon.com/512/4498/4498041.png",
                contentDescription = null,
                loading = placeholder,
                failure = placeholder
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            outcomes.forEach { outcome ->
                OddsOutcomeRow(outcome = outcome, onOutcomeSelected = onOutcomeSelected)
            }
        }
    }
}

@Composable
fun OddsOutcomeRow(outcome: Outcome, onOutcomeSelected: (Outcome) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = outcome.name, modifier = Modifier.weight(1f))
        Button(onClick = { onOutcomeSelected(outcome) }) {
            Text(text = outcome.price.formatAmount())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OddsComposablePreview() {
    val outcomes = listOf(
        Outcome("Gazişehir Gaziantep", 1.3), Outcome("Alanyaspor", 1.65), Outcome("Draw", 2.30)
    )
    OddsComposable(bookmakerTitle = "DraftKings", outcomes = outcomes, onOutcomeSelected = { outcome ->
        println("Selected outcome: ${outcome.name} with odds ${outcome.price}")
    })
}