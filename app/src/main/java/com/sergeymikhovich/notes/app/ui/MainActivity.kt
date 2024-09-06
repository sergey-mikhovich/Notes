package com.sergeymikhovich.notes.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sergeymikhovich.notes.app.mediator.graph.composableAll
import com.sergeymikhovich.notes.app.ui.theme.NotesTheme
import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.feature.splash.navigation.SplashDirection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            
            LaunchedEffect(navController) {
                navigator.navActions.collect { direction ->
                    when (direction) {
                        is Navigator.Direction.Forward -> {
                            navController.navigate(
                                direction.navAction.destination,
                                direction.navAction.navOptions
                            )
                        }
                        is Navigator.Direction.Back -> navController.navigateUp()
                    }
                }
            }

            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = SplashDirection.route,
                        builder = NavGraphBuilder::composableAll
                    )
                }
            }
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Composable
fun NotesPreview() {
    NotesTheme {
    }
}