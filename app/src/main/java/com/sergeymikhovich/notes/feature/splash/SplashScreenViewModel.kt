package com.sergeymikhovich.notes.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.feature.splash.navigation.SplashRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val splashRouter: SplashRouter
): ViewModel(), SplashRouter by splashRouter {

    fun onAppStart() {
        viewModelScope.launch {
            if (accountRepository.hasUser()) toNotes() else toSignIn()
        }
    }
}