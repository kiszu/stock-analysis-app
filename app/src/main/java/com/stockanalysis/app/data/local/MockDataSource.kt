package com.stockanalysis.app.data.local

import com.stockanalysis.app.domain.model.AnalysisResult
import com.stockanalysis.app.domain.model.NewsItem
import com.stockanalysis.app.domain.model.Signal
import com.stockanalysis.app.domain.model.SignalDetail
import com.stockanalysis.app.domain.model.SignalType
import com.stockanalysis.app.domain.model.RiskLevel
import com.stockanalysis.app.domain.model.TechnicalIndicators
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mock data source for prototype - provides sample data without API
 */
@Singleton
class MockDataSource @Inject constructor() {

    fun getFeaturedSignals(): List<Signal> = listOf(
        Signal(
            id = "1",
            symbol = "AAPL",
            name = "Apple Inc.",
            signalType = SignalType.BUY,
            confidence = 0.87f,
            currentPrice = 178.50,
            targetPrice = 185.20,
            percentChange = 3.8,
            timestamp = System.currentTimeMillis(),
            source = "Technical",
            isRealTime = true
        ),
        Signal(
            id = "2",
            symbol = "MSFT",
            name = "Microsoft Corporation",
            signalType = SignalType.STRONG_BUY,
            confidence = 0.92f,
            currentPrice = 378.91,
            targetPrice = 400.00,
            percentChange = 5.2,
            timestamp = System.currentTimeMillis(),
            source = "Fundamental",
            isRealTime = true
        ),
        Signal(
            id = "3",
            symbol = "GOOGL",
            name = "Alphabet Inc.",
            signalType = SignalType.BUY,
            confidence = 0.78f,
            currentPrice = 141.80,
            targetPrice = 150.00,
            percentChange = 2.1,
            timestamp = System.currentTimeMillis(),
            source = "Technical",
            isRealTime = true
        ),
        Signal(
            id = "4",
            symbol = "TSLA",
            name = "Tesla Inc.",
            signalType = SignalType.SELL,
            confidence = 0.72f,
            currentPrice = 248.50,
            targetPrice = 235.00,
            percentChange = -4.3,
            timestamp = System.currentTimeMillis(),
            source = "Technical",
            isRealTime = true
        ),
        Signal(
            id = "5",
            symbol = "NVDA",
            name = "NVIDIA Corporation",
            signalType = SignalType.STRONG_BUY,
            confidence = 0.95f,
            currentPrice = 495.22,
            targetPrice = 550.00,
            percentChange = 8.5,
            timestamp = System.currentTimeMillis(),
            source = "AI Trend",
            isRealTime = true
        ),
        Signal(
            id = "6",
            symbol = "AMZN",
            name = "Amazon.com Inc.",
            signalType = SignalType.BUY,
            confidence = 0.81f,
            currentPrice = 153.42,
            targetPrice = 165.00,
            percentChange = 2.8,
            timestamp = System.currentTimeMillis(),
            source = "Technical",
            isRealTime = true
        )
    )

    fun getSignalDetail(symbol: String): SignalDetail {
        val baseSignal = getFeaturedSignals().find { it.symbol == symbol }
            ?: getFeaturedSignals().first()

        return SignalDetail(
            signal = baseSignal,
            technicalIndicators = TechnicalIndicators(
                rsi = 65.4f,
                macd = "Bullish",
                movingAverage50 = 175.20,
                movingAverage200 = 168.50,
                bollingerUpper = 185.00,
                bollingerLower = 170.00
            ),
            supportLevel = baseSignal.currentPrice * 0.95,
            resistanceLevel = baseSignal.targetPrice,
            volumeAnalysis = "Volume increased by 25% above average",
            trendDescription = "Strong uptrend with increasing momentum. RSI indicates moderate buying pressure.",
            riskLevel = RiskLevel.MEDIUM
        )
    }

    fun getMarketNews(): List<NewsItem> = listOf(
        NewsItem(
            id = "1",
            title = "Fed Signals Potential Rate Cuts in Q2",
            summary = "Federal Reserve officials indicated they may begin cutting interest rates in the second quarter of 2024.",
            source = "Reuters",
            url = "https://example.com/news/1",
            imageUrl = null,
            timestamp = System.currentTimeMillis(),
            relatedSymbols = listOf("SPY", "QQQ", "AAPL")
        ),
        NewsItem(
            id = "2",
            title = "AI Chip Demand Drives NVIDIA to Record Highs",
            summary = "NVIDIA continues to benefit from insatiable demand for AI accelerators across data centers.",
            source = "Bloomberg",
            url = "https://example.com/news/2",
            imageUrl = null,
            timestamp = System.currentTimeMillis() - 3600000,
            relatedSymbols = listOf("NVDA", "AMD", "INTC")
        ),
        NewsItem(
            id = "3",
            title = "Tesla Earnings Beat Expectations",
            summary = "Tesla reports better-than-expected Q4 earnings, driven by strong EV deliveries and energy storage growth.",
            source = "CNBC",
            url = "https://example.com/news/3",
            imageUrl = null,
            timestamp = System.currentTimeMillis() - 7200000,
            relatedSymbols = listOf("TSLA", "RIVN", "NIO")
        ),
        NewsItem(
            id = "4",
            title = "Apple Vision Pro Pre-orders Sell Out",
            summary = "Apple's Vision Pro pre-orders sold out within hours, signaling strong demand for the new spatial computing device.",
            source = "The Verge",
            url = "https://example.com/news/4",
            imageUrl = null,
            timestamp = System.currentTimeMillis() - 10800000,
            relatedSymbols = listOf("AAPL", "MSFT", "GOOGL")
        )
    )

    fun searchSymbols(query: String): List<Signal> {
        val allSignals = getFeaturedSignals()
        return allSignals.filter {
            it.symbol.contains(query, ignoreCase = true) ||
            it.name.contains(query, ignoreCase = true)
        }
    }
}
