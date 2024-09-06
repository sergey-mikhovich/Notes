package com.sergeymikhovich.notes.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(color = MaterialTheme.colors.onBackground, strokeCap = StrokeCap.Round)
    }
    
    LaunchedEffect(Unit) {
        delay(SPLASH_TIMEOUT)
        viewModel.onAppStart()
    }
}