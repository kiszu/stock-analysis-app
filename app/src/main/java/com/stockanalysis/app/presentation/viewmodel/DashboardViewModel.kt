package com.stockanalysis.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stockanalysis.app.data.repository.StockRepository
import com.stockanalysis.app.domain.model.AnalysisResult
import com.stockanalysis.app.domain.model.NewsItem
import com.stockanalysis.app.domain.model.Signal
import com.stockanalysis.app.domain.model.SignalDetail
import com.stockanalysis.app.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * ViewModel for Dashboard screen
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadFeaturedSignals()
        loadMarketNews()
    }

    fun loadFeaturedSignals() {
        repository.getFeaturedSignals()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            featuredSignals = result.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loadMarketNews() {
        repository.getMarketNews()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(
                            marketNews = result.data ?: emptyList()
                        )
                    }
                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        loadFeaturedSignals()
        loadMarketNews()
    }
}

/**
 * UI State for Dashboard
 */
data class DashboardUiState(
    val featuredSignals: List<Signal> = emptyList(),
    val marketNews: List<NewsItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
