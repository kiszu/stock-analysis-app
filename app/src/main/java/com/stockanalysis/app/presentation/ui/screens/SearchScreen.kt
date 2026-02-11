package com.stockanalysis.app.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stockanalysis.app.domain.model.Signal
import com.stockanalysis.app.domain.model.SignalType

/**
 * Search Screen - Search for stock symbols
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSymbolClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf<List<Signal>>(emptyList()) }
    var isSearching by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { query ->
                    searchQuery = query
                    if (query.length >= 2) {
                        isSearching = true
                        // TODO: Implement actual search
                        // For now, show placeholder results
                        searchResults = getPlaceholderResults(query)
                    } else {
                        searchResults = emptyList()
                        isSearching = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search stocks...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                singleLine = true
            )

            // Results
            if (isSearching) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(searchResults) { signal ->
                        SearchResultItem(
                            signal = signal,
                            onClick = { onSymbolClick(signal.symbol) }
                        )
                    }
                }
            } else {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Type at least 2 characters to search",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun SearchResultItem(
    signal: Signal,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    signal.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    signal.name,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "$${String.format("%.2f", signal.currentPrice)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                SignalBadge(signalType = signal.signalType)
            }
        }
    }
}

// Placeholder results for demo
fun getPlaceholderResults(query: String): List<Signal> {
    return listOf(
        Signal(
            id = "1",
            symbol = query.uppercase().take(4),
            name = "$query Inc.",
            signalType = SignalType.BUY,
            confidence = 0.85f,
            currentPrice = 178.50,
            targetPrice = 185.20,
            percentChange = 3.8,
            timestamp = System.currentTimeMillis(),
            source = "Technical",
            isRealTime = true
        ),
        Signal(
            id = "2",
            symbol = "${query.uppercase().take(2)}O",
            name = "${query.uppercase()} Corp",
            signalType = SignalType.HOLD,
            confidence = 0.55f,
            currentPrice = 92.30,
            targetPrice = 95.00,
            percentChange = 1.2,
            timestamp = System.currentTimeMillis(),
            source = "Fundamental",
            isRealTime = false
        )
    )
}
