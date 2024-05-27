package com.germanautolabs.newsapp.feature_articles.presentation.article_listing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.germanautolabs.newsapp.R
import com.germanautolabs.newsapp.feature_articles.domain.model.ArticleListing

@Composable
fun ArticleItem(
    article: ArticleListing,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(
                    text = article.title ?: stringResource(R.string.no_title),
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.source ?: stringResource(R.string.unknown),
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.publishedAt,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = MaterialTheme.colors.onBackground
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.description?: "-",
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                maxLines = 2,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}