package com.stockanalysis.app.data.repository

import com.stockanalysis.app.data.api.StockApi
import com.stockanalysis.app.data.local.MockDataSource
import com.stockanalysis.app.domain.model.AnalysisResult
import com.stockanalysis.app.domain.model.NewsItem
import com.stockanalysis.app.domain.model.Signal
import com.stockanalysis.app.domain.model.SignalDetail
import com.stockanalysis.app.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for stock data operations
 * Uses mock data for prototype, real API for production
 */
@Singleton
class StockRepository @Inject constructor(
    private val api: StockApi,
    private val mockData: MockDataSource
) {
    // Toggle this to use real API
    private val useMockData = true // Set to false when API is ready

    /**
     * Get featured signals for dashboard
     */
    fun getFeaturedSignals(): Flow<Resource<List<Signal>>> = flow {
        emit(Resource.Loading())
        
        if (useMockData) {
            // Simulate network delay
            delay(500)
            val signals = mockData.getFeaturedSignals()
            emit(Resource.Success(signals))
        } else {
            try {
                val response = api.getFeaturedSignals()
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Failed to fetch signals"))
            }
        }
    }

    /**
     * Get detailed signal analysis
     */
    fun getSignalDetail(symbol: String): Flow<Resource<SignalDetail>> = flow {
        emit(Resource.Loading())
        
        if (useMockData) {
            delay(300)
            val detail = mockData.getSignalDetail(symbol)
            emit(Resource.Success(detail))
        } else {
            try {
                val response = api.getSignal(symbol)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Failed to fetch signal detail"))
            }
        }
    }

    /**
     * Get market news
     */
    fun getMarketNews(limit: Int = 10): Flow<Resource<List<NewsItem>>> = flow {
        emit(Resource.Loading())
        
        if (useMockData) {
            delay(400)
            val news = mockData.getMarketNews().take(limit)
            emit(Resource.Success(news))
        } else {
            try {
                val response = api.getNews(limit = limit)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Failed to fetch news"))
            }
        }
    }

    /**
     * Search for symbols
     */
    fun searchSymbol(query: String): Flow<Resource<List<Signal>>> = flow {
        if (query.isBlank()) {
            emit(Resource.Success(emptyList()))
            return@flow
        }
        
        if (useMockData) {
            delay(200)
            val results = mockData.searchSymbols(query)
            emit(Resource.Success(results))
        } else {
            try {
                val response = api.searchSymbol(query)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Search failed"))
            }
        }
    }

    /**
     * Get analysis for a symbol
     */
    fun getAnalysis(symbol: String): Flow<Resource<AnalysisResult>> = flow {
        emit(Resource.Loading())
        
        if (useMockData) {
            delay(600)
            // Return mock analysis
            val mockSignal = mockData.getFeaturedSignals().find { it.symbol == symbol }
                ?: mockData.getFeaturedSignals().first()
            
            val analysis = AnalysisResult(
                symbol = symbol,
                overallScore = mockSignal.confidence,
                technicalScore = mockSignal.confidence + 0.05f,
                fundamentalScore = mockSignal.confidence - 0.02f,
                sentimentScore = mockSignal.confidence + 0.03f,
                recommendation = mockSignal.signalType,
                keyMetrics = mapOf(
                    "PE Ratio" to 25.4,
                    "EPS" to 5.82,
                    "Market Cap" to 2.8e12,
                    "Dividend Yield" to 0.52
                ),
                analysisText = "Strong technical setup with bullish momentum. RSI at 65.4 indicates room for continued growth. MACD shows bullish crossover. Support level established at previous resistance.",
                timestamp = System.currentTimeMillis()
            )
            emit(Resource.Success(analysis))
        } else {
            try {
                val response = api.getAnalysis(symbol)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "Failed to fetch analysis"))
            }
        }
    }
}
