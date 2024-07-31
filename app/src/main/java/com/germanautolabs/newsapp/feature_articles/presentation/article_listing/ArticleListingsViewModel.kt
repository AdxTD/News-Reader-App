package com.germanautolabs.newsapp.feature_articles.presentation.article_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.germanautolabs.newsapp.core.common.Resource
import com.germanautolabs.newsapp.feature_articles.domain.usecases.GetArticlesUseCase
import com.germanautolabs.newsapp.feature_articles.domain.utils.ArticlesOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListingsViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    var state by mutableStateOf(ArticleListingsState())
    private var searchJob: Job? = null

    init {
        getArticleListings()
    }

    fun onEvent(event: ArticleListingsEvent) {
        when (event) {
            is ArticleListingsEvent.Refresh -> {
                getArticleListings(fetchFromRemote = true)
            }

            is ArticleListingsEvent.OnSearchQueryChange -> {
                state = state.copy(
                    searchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000L)
                    getArticleListings()
                }
            }

            is ArticleListingsEvent.Order -> {
                if (state.articlesOrder::class == event.articlesOrder::class &&
                    state.articlesOrder.orderType == event.articlesOrder.orderType
                )
                    return
                getArticleListings(articlesOrder = event.articlesOrder)
            }

            is ArticleListingsEvent.ToggleOrderSection -> state = state.copy(
                isOrderSectionVisible = !state.isOrderSectionVisible
            )
        }
    }

    private fun getArticleListings(
        articlesOrder: ArticlesOrder = state.articlesOrder,
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) =
        viewModelScope.launch {
            getArticlesUseCase(
                articlesOrder = articlesOrder,
                fetchFromRemote = fetchFromRemote,
                searchQuery = query
            ).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { articleListings ->
                            state = state.copy(
                                articles = articleListings,
                                articlesOrder = articlesOrder,
                                error = ""
                            )
                        }
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            error = result.message ?: "Unexpected error occurred!"
                        )
                    }
                }
            }
        }

}