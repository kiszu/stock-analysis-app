package com.stockanalysis.app.data.api

import com.stockanalysis.app.domain.model.AnalysisResult
import com.stockanalysis.app.domain.model.NewsItem
import com.stockanalysis.app.domain.model.Signal
import com.stockanalysis.app.domain.model.SignalDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API interface for Stock Analysis backend
 */
interface StockApi {

    @GET("api/v1/signals")
    suspend fun getSignals(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0,
        @Query("tier") tier: String = "free"
    ): List<Signal>

    @GET("api/v1/signals/featured")
    suspend fun getFeaturedSignals(): List<Signal>

    @GET("api/v1/signals/{symbol}")
    suspend fun getSignal(
        @Path("symbol") symbol: String
    ): SignalDetail

    @GET("api/v1/analysis/{symbol}")
    suspend fun getAnalysis(
        @Path("symbol") symbol: String
    ): AnalysisResult

    @GET("api/v1/market/news")
    suspend fun getNews(
        @Query("limit") limit: Int = 10
    ): List<NewsItem>

    @GET("api/v1/search")
    suspend fun searchSymbol(
        @Query("q") query: String
    ): List<Signal>
}
