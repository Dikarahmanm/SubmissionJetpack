package com.dika.starrail.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CustomLoadingIndicator() {
    Box(modifier = Modifier
        .fillMaxSize()
        , contentAlignment = Alignment.Center ) {
        CircularProgressIndicator()
    }
}