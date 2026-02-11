package com.stockanalysis.app.data.repository

import com.stockanalysis.app.domain.model.AnalysisResult
import com.stockanalysis.app.domain.model.NewsItem
import com.stockanalysis.app.domain.model.Signal
import com.stockanalysis.app.domain.model.SignalDetail
import com.stockanalysis.app.domain.model.SignalType
import com.stockanalysis.app.domain.model.RiskLevel
import com.stockanalysis.app.domain.model.TechnicalIndicators
import com.stockanalysis.app.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Repository with mock data for prototype
 */
class StockRepository {

    private val mockData = MockDataSource()

    fun getFeaturedSignals(): Flow<Resource<List<Signal>>> = flow {
        emit(Resource.Loading())
        delay(500)
        emit(Resource.Success(mockData.getFeaturedSignals()))
    }

    fun getSignalDetail(symbol: String): Flow<Resource<SignalDetail>> = flow {
        emit(Resource.Loading())
        delay(300)
        emit(Resource.Success(mockData.getSignalDetail(symbol)))
    }

    fun getMarketNews(limit: Int = 10): Flow<Resource<List<NewsItem>>> = flow {
        emit(Resource.Loading())
        delay(400)
        emit(Resource.Success(mockData.getMarketNews().take(limit)))
    }

    fun searchSymbol(query: String): Flow<Resource<List<Signal>>> = flow {
        if (query.isBlank()) {
            emit(Resource.Success(emptyList()))
            return@flow
        }
        delay(200)
        emit(Resource.Success(mockData.searchSymbols(query)))
    }
}

/**
 * Mock data source
 */
class MockDataSource {

    fun getFeaturedSignals(): List<Signal> = listOf(
        Signal("1", "NVDA", "NVIDIA Corporation", SignalType.STRONG_BUY, 0.95f, 495.22, 550.00, 8.52, System.currentTimeMillis(), "AI Trend", true),
        Signal("2", "AAPL", "Apple Inc.", SignalType.BUY, 0.87f, 178.50, 185.20, 3.82, System.currentTimeMillis(), "Technical", true),
        Signal("3", "MSFT", "Microsoft Corporation", SignalType.STRONG_BUY, 0.92f, 378.91, 400.00, 5.21, System.currentTimeMillis(), "Fundamental", true),
        Signal("4", "TSLA", "Tesla Inc.", SignalType.SELL, 0.72f, 248.50, 235.00, -4.31, System.currentTimeMillis(), "Technical", true),
        Signal("5", "GOOGL", "Alphabet Inc.", SignalType.BUY, 0.78f, 141.80, 150.00, 2.15, System.currentTimeMillis(), "Technical", true),
        Signal("6", "AMZN", "Amazon.com Inc.", SignalType.BUY, 0.81f, 153.42, 165.00, 2.78, System.currentTimeMillis(), "Technical", true)
    )

    fun getSignalDetail(symbol: String): SignalDetail {
        val baseSignal = getFeaturedSignals().find { it.symbol == symbol } ?: getFeaturedSignals().first()

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
            trendDescription = "Strong uptrend with increasing momentum.",
            riskLevel = RiskLevel.MEDIUM
        )
    }

    fun getMarketNews(): List<NewsItem> = listOf(
        NewsItem("1", "Fed Signals Rate Cuts", "Federal Reserve indicated potential rate cuts.", "Reuters", "", null, System.currentTimeMillis(), listOf("SPY", "QQQ")),
        NewsItem("2", "AI Chip Demand Surge", "NVIDIA benefits from AI accelerator demand.", "Bloomberg", "", null, System.currentTimeMillis() - 3600000, listOf("NVDA", "AMD")),
        NewsItem("3", "Tesla Earnings Beat", "Better-than-expected Q4 earnings.", "CNBC", "", null, System.currentTimeMillis() - 7200000, listOf("TSLA"))
    )

    fun searchSymbols(query: String): List<Signal> {
        return getFeaturedSignals().filter {
            it.symbol.contains(query, ignoreCase = true) ||
            it.name.contains(query, ignoreCase = true)
        }
    }
}
