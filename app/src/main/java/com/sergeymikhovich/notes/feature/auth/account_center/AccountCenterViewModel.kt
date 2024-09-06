package com.sergeymikhovich.notes.feature.auth.account_center

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.core.model.User
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountCenterViewModel @Inject constructor(
    private val accountRepository: AccountRepository,
    private val router: AccountCenterRouter
): ViewModel(), AccountCenterRouter by router {

    val user: StateFlow<User> =
        accountRepository.observeCurrentUser()
            .filterNotNull()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                User()
            )

    fun onSignOut() {
        viewModelScope.launch {
            accountRepository.signOut()
            toSplash()
        }
    }

    fun onDeleteAccount() {
        viewModelScope.launch {
            accountRepository.deleteAccount()
            toSplash()
        }
    }
}