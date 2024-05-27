package com.germanautolabs.newsapp.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ImageCard(
    linkToImage: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(
            modifier = Modifier.height(300.dp)
        ) {
            AsyncImage(
                model = linkToImage,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
        }
    }
}