package com.germanautolabs.newsapp.feature_articles.presentation.article_listing.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.germanautolabs.newsapp.core.ui.components.DefaultRadioButton
import com.germanautolabs.newsapp.feature_articles.domain.utils.ArticlesOrder
import com.germanautolabs.newsapp.feature_articles.domain.utils.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    articlesOrder: ArticlesOrder = ArticlesOrder.Date(OrderType.Descending),
    onOrderChanged: (ArticlesOrder) -> Unit
){
    Column(
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text ="Title",
                selected = articlesOrder is ArticlesOrder.Title,
                onSelect = { onOrderChanged(ArticlesOrder.Title(articlesOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text ="Date",
                selected = articlesOrder is ArticlesOrder.Date,
                onSelect = { onOrderChanged(ArticlesOrder.Date(articlesOrder.orderType)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text ="Ascending",
                selected = articlesOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChanged(articlesOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text ="Descending",
                selected = articlesOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChanged(articlesOrder.copy(OrderType.Descending )) }
            )
        }
    }

}