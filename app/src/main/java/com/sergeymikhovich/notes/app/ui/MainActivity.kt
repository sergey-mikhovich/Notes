package com.sergeymikhovich.notes.app.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.sergeymikhovich.notes.app.mediator.graph.composableAll
import com.sergeymikhovich.notes.app.ui.theme.NotesTheme
import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInDirection
import com.sergeymikhovich.notes.feature.notes.navigation.NotesDirection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var accountRepository: AccountRepository

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
                    val startDestination =
                        if (accountRepository.hasUser()) NotesDirection.route else SignInDirection.route

                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        builder = NavGraphBuilder::composableAll
                    )
                }
            }
        }
    }
}