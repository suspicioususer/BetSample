package com.project.falconic_solutions.betsample.ui.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.project.falconic_solutions.betsample.R
import com.project.falconic_solutions.betsample.data.DateFormat
import com.project.falconic_solutions.betsample.data.extension.formatDate
import com.project.falconic_solutions.betsample.data.model.response.Event

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventRow(
    event: Event,
    onEventSelected: (Event) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onEventSelected(event)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(0.dp)
                    .wrapContentHeight()
                    .weight(1f, true),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val matchInfo = "${event.homeTeam} - ${event.awayTeam}"
                val date = event.commenceTime.formatDate(DateFormat.DateTimeSlash.pattern)
                Text(text = matchInfo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = date, fontStyle = FontStyle.Italic, fontSize = 12.sp)
            }
            val placeholder = placeholder(R.drawable.ic_launcher_foreground)
            GlideImage(
                modifier = Modifier.size(32.dp),
                model = "https://cdn-icons-png.flaticon.com/512/4498/4498041.png",
                contentDescription = null,
                loading = placeholder,
                failure = placeholder
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EventRowPreview() {
    val event = Event(
        id = "1",
        sportKey = "",
        sportTitle = "Turkey Super League",
        commenceTime = "2025-05-09T17:00:00Z",
        homeTeam = "Alanyaspor",
        awayTeam = "Gazişehir Gaziantep",
        bookmakers = emptyList()
    )
    EventRow(
        event = event,
        onEventSelected = { event ->
            println("Selected event: ${event.id} ")
        }
    )
}