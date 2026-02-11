package com.stockanalysis.app.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stockanalysis.app.domain.model.Signal
import com.stockanalysis.app.domain.model.SignalType
import com.stockanalysis.app.presentation.viewmodel.DashboardViewModel

/**
 * Signal Detail Screen - Full analysis view
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignalDetailScreen(
    symbol: String,
    onBackClick: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val signal = uiState.featuredSignals.find { it.symbol == symbol }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(symbol) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (signal == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Signal Header
                SignalHeader(signal)

                Spacer(modifier = Modifier.height(24.dp))

                // Price Information
                PriceSection(signal)

                Spacer(modifier = Modifier.height(24.dp))

                // Confidence Meter
                ConfidenceSection(signal.confidence)

                Spacer(modifier = Modifier.height(24.dp))

                // Technical Indicators (placeholder)
                TechnicalIndicatorsSection()

                Spacer(modifier = Modifier.weight(1f))

                // Action Button
                ActionButton(signal)
            }
        }
    }
}

@Composable
fun SignalHeader(signal: Signal) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                signal.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "Current Price: $${String.format("%.2f", signal.currentPrice)}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        SignalBadge(signalType = signal.signalType)
    }
}

@Composable
fun PriceSection(signal: Signal) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Price Information",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                PriceItem("Target", "$${String.format("%.2f", signal.targetPrice)}")
                PriceItem("Change", "${if (signal.percentChange >= 0) "+" else ""}${String.format("%.2f", signal.percentChange)}%")
                PriceItem("Source", signal.source)
            }
        }
    }
}

@Composable
fun PriceItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ConfidenceSection(confidence: Float) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Signal Confidence",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            LinearProgressIndicator(
                progress = { confidence },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = when {
                    confidence >= 0.8f -> MaterialTheme.colorScheme.primary
                    confidence >= 0.6f -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.error
                }
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                "${String.format("%.0f", confidence * 100)}% confidence",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TechnicalIndicatorsSection() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "Technical Indicators",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            // Placeholder for technical indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IndicatorItem("RSI", "65.4")
                IndicatorItem("MACD", "Bullish")
                IndicatorItem("MA50", "Bullish")
                IndicatorItem("MA200", "Bullish")
            }
        }
    }
}

@Composable
fun IndicatorItem(name: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            name,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ActionButton(signal: Signal) {
    Button(
        onClick = { /* Open trading platform */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (signal.signalType) {
                SignalType.BUY, SignalType.STRONG_BUY -> MaterialTheme.colorScheme.primary
                SignalType.SELL, SignalType.STRONG_SELL -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.secondary
            }
        )
    ) {
        Text(
            "Open in Trading App",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
