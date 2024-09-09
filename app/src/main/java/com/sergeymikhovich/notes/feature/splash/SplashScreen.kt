package com.sergeymikhovich.notes.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.feature.splash.navigation.SplashDirection
import kotlinx.coroutines.delay

private const val SPLASH_TIMEOUT = 1000L

fun NavGraphBuilder.composableToSplash() = composableTo(SplashDirection) { SplashScreen() }

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    SplashContent()
    
    LaunchedEffect(Unit) {
        delay(SPLASH_TIMEOUT)
        viewModel.onAppStart()
    }
}

@Composable
private fun SplashContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF8EEE2)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .height(10.dp),
            color = Color(0xFFD9614C),
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    SplashContent()
}