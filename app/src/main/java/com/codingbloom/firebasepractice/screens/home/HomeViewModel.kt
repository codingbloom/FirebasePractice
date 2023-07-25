
package com.codingbloom.firebasepractice.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingbloom.firebasepractice.LOGIN_SCREEN
import com.codingbloom.firebasepractice.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val accountService: AccountService,
) : ViewModel() {

  fun goForSignUp(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

  fun onSignOutClick(restartApp: (String) -> Unit) {
    viewModelScope.launch {
      accountService.signOut()
      restartApp(LOGIN_SCREEN)
    }
  }

  fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
    viewModelScope.launch {
      accountService.deleteAccount()
      restartApp(LOGIN_SCREEN)
    }
  }
}
