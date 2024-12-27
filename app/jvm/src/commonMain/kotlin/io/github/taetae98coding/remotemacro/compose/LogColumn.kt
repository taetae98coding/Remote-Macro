package io.github.taetae98coding.remotemacro.compose

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LogColumn(
    state: MacroState,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        val lazyListState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp)
        ) {
            items(
                items = state.messageList,
                key = { it.key },
                contentType = { "Log" }
            ) {
                Row {
                    Text(
                        text = it.message,
                        modifier = Modifier.weight(1F)
                            .basicMarquee(iterations = Int.MAX_VALUE),
                        maxLines = 1,
                    )
                    Text(
                        text = it.dateTime.toString(),
                        modifier = Modifier.weight(1F)
                            .basicMarquee(iterations = Int.MAX_VALUE),
                        maxLines = 1,
                    )
                }
            }
        }

        LaunchedEffect(lazyListState, state.messageList) {
            if (state.messageList.isNotEmpty()) {
                lazyListState.animateScrollToItem(state.messageList.size - 1)
            }
        }
    }
}