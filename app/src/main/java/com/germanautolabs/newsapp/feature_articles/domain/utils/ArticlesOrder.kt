package com.germanautolabs.newsapp.feature_articles.domain.utils

sealed class ArticlesOrder(
    val orderType: OrderType
) {
    class Title(orderType: OrderType) : ArticlesOrder(orderType)
    class Date(orderType: OrderType) : ArticlesOrder(orderType)

    fun copy(orderType: OrderType) : ArticlesOrder {
        return when(this){
            is Title -> Title(orderType)
            is Date -> Date(orderType)
        }
    }
}