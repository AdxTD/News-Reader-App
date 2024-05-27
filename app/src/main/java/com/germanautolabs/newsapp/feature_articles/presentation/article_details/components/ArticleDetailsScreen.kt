package com.germanautolabs.newsapp.feature_articles.presentation.article_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.germanautolabs.newsapp.core.ui.components.ImageCard
import com.germanautolabs.newsapp.feature_articles.presentation.article_details.ArticleDetailsViewModel
import com.germanautolabs.newsapp.ui.theme.DarkBlue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Composable
@Destination<RootGraph>
fun ArticleDetailsScreen(
    id: Int,
    articleDetailsViewModel: ArticleDetailsViewModel = hiltViewModel()
) {
    val state = articleDetailsViewModel.state

    state.articleDetails?.let { articleDetails ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(DarkBlue)
        ) {

            articleDetails.urlToImage?.let {
                ImageCard(
                    linkToImage = it,
                    contentDescription = articleDetails.description ?: "No description",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                    text = articleDetails.title ?: "No title",
                     fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis
                )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = articleDetails.source ?: "Unknown publisher",
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                fontStyle = FontStyle.Italic,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Divider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Author: ${articleDetails.author}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Published at: ${articleDetails.publishedAt}",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                articleDetails.description ?: "No description",
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Divider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Link to article: ${articleDetails.url}",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                articleDetails.content ?: "No content",
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if(state.isLoading) {
            CircularProgressIndicator()
        } else if (state.error != null) {
            Text(
                text = state.error,
                color = MaterialTheme.colors.error
            )
        }
    }
}