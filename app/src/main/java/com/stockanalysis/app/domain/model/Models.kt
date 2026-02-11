package com.stockanalysis.app.domain.model

/**
 * Trading signal data model
 */
data class Signal(
    val id: String,
    val symbol: String,
    val name: String,
    val signalType: SignalType,
    val confidence: Float,
    val currentPrice: Double,
    val targetPrice: Double,
    val percentChange: Double,
    val timestamp: Long,
    val source: String,
    val isRealTime: Boolean = false
)

enum class SignalType {
    BUY, SELL, HOLD, STRONG_BUY, STRONG_SELL
}

/**
 * Detailed signal with full analysis
 */
data class SignalDetail(
    val signal: Signal,
    val technicalIndicators: TechnicalIndicators,
    val supportLevel: Double,
    val resistanceLevel: Double,
    val volumeAnalysis: String,
    val trendDescription: String,
    val riskLevel: RiskLevel
)

data class TechnicalIndicators(
    val rsi: Float,
    val macd: String,
    val movingAverage50: Double,
    val movingAverage200: Double,
    val bollingerUpper: Double,
    val bollingerLower: Double
)

enum class RiskLevel {
    LOW, MEDIUM, HIGH
}

/**
 * Analysis result from technical/fundamental analysis
 */
data class AnalysisResult(
    val symbol: String,
    val overallScore: Float,
    val technicalScore: Float,
    val fundamentalScore: Float,
    val sentimentScore: Float,
    val recommendation: SignalType,
    val keyMetrics: Map<String, Double>,
    val analysisText: String,
    val timestamp: Long
)

/**
 * Market news item
 */
data class NewsItem(
    val id: String,
    val title: String,
    val summary: String,
    val source: String,
    val url: String,
    val imageUrl: String?,
    val timestamp: Long,
    val relatedSymbols: List<String>
)
