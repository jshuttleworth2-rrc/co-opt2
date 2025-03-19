package com.shuttle.co_opt2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shuttle.co_opt2.ui.theme.Co_opt2Theme
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState
import androidx.compose.foundation.layout.Arrangement

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Co_opt2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ReorderList()
                }
            }
        }
    }
}

@Composable
fun CustomList() {
    val itemList = List(25) { "Item $it" }
    LazyColumn {
        items(itemList) { item ->
            Card( // Create a "card" around items, similar to MovieCards Demo
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp) // Create space between card items
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp) // Add padding inside the card
                )
            }
        }
    }
}

/*
 * https://github.com/Calvin-LL/Reorderable
 */
@Composable
fun ReorderList() {
    val itemList = remember { mutableStateListOf(*List(25) { "Item $it"}.toTypedArray()) }
    val lazyListState = rememberLazyListState() // Remembers the state

    val reorderableState = rememberReorderableLazyListState(lazyListState = lazyListState) { from, to ->
            itemList.add(to.index, itemList.removeAt(from.index)) // Tracks changes and updates the states from index to index
    }

    LazyColumn(
        state = lazyListState, // Assigns the state defined
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(itemList, key = { it }) { item -> // Lists the items
            ReorderableItem(reorderableState, key = item) { isDragging -> // Wraps the items to allow for dragging and reordering
                Row {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .let { if (isDragging) it else it }
                    ) {
                        Row (
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = item,
                                modifier = Modifier.padding(16.dp)
                            )

                            IconButton(
                                // The icon that allows users to actually drag and reorder the lists
                                modifier = Modifier.draggableHandle(),
                                onClick = {},
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.drag_lines),
                                    contentDescription = "Snowflake"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
