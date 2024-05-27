package com.germanautolabs.newsapp.feature_articles.presentation.article_listing.components

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.germanautolabs.newsapp.R
import com.germanautolabs.newsapp.feature_articles.presentation.article_listing.ArticleListingsEvent
import com.germanautolabs.newsapp.feature_articles.presentation.article_listing.ArticleListingsViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.ArticleDetailsScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@Destination<RootGraph>(start = true)
fun ArticleListingsScreen(
        navigator: DestinationsNavigator,
        viewModel: ArticleListingsViewModel = hiltViewModel()
    ){
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    val state = viewModel.state
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //TODO: call
                },
                backgroundColor = MaterialTheme.colors.primary
            ){
                Icon(imageVector = Icons.Default.Mic, contentDescription = stringResource(R.string.listen) )
            }
        },
        scaffoldState = scaffoldState
    ){
        Column (modifier = Modifier.fillMaxSize()){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =  Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = {
                        viewModel.onEvent(ArticleListingsEvent.OnSearchQueryChange(it))
                    },
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .padding(start = 16.dp)
                        .fillMaxWidth(.90f),
                    placeholder = {
                        Text(text = stringResource(R.string.search))
                    },
                    maxLines = 1,
                    singleLine = true
                )
                IconButton(onClick = { viewModel.onEvent(ArticleListingsEvent.ToggleOrderSection) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        //.testTag(TestTags.ORDER_SECTION),
                    articlesOrder = state.articlesOrder,
                    onOrderChanged = {
                        viewModel.onEvent(ArticleListingsEvent.Order(it))
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { viewModel.onEvent(ArticleListingsEvent.Refresh) }
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(count = state.articles.size){i ->
                        val article = state.articles[i]
                        ArticleItem(
                            article = article,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .clickable {
                                    article.id?.let { id ->
                                        navigator.navigate(
                                            ArticleDetailsScreenDestination(id)
                                        )
                                    }

                                }
                        )
                        if(i < state.articles.size){
                            Divider(modifier = Modifier.padding(
                                horizontal = 16.dp
                            ))
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            if(state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error.isNotBlank()) {
                if(state.articles.isEmpty()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error
                    )
                }
                scope.launch {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = state.error,
                        actionLabel = "Refresh"
                    )
                    if(result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(ArticleListingsEvent.Refresh)
                    }
                }
            } else if(state.error.isBlank() && state.articles.isEmpty()){
                Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ManageSearch,
                        contentDescription = stringResource(R.string.no_results_icon)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.no_results_found),
                        color = MaterialTheme.colors.onBackground
                    )

                }
            }
        }
    }
}