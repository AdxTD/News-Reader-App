package com.germanautolabs.newsapp.feature_articles.presentation.article_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.germanautolabs.newsapp.core.common.Resource
import com.germanautolabs.newsapp.feature_articles.domain.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    private val repository: ArticleRepository,
    private val savedStateHandle: SavedStateHandle
) :ViewModel() {

    var state by mutableStateOf(ArticleDetailsState())

    init {
        viewModelScope.launch {
            val id = savedStateHandle.get<Int>("id") ?: return@launch
            state = state.copy(
                isLoading = true
            )
            val articleDetails = async { repository.getArticleDetailsById(id) }

            when(val result = articleDetails.await()){
                is Resource.Success -> {
                    state = state.copy(
                        articleDetails = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Loading -> Unit
                is Resource.Error -> {
                    state = state.copy(
                        articleDetails = null,
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}