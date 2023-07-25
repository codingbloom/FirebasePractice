
package com.codingbloom.firebasepractice.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingbloom.firebasepractice.HOME_SCREEN
import com.codingbloom.firebasepractice.R.string as AppText
import com.codingbloom.firebasepractice.SIGN_UP_SCREEN
import com.codingbloom.firebasepractice.common.ext.isValidEmail
import com.codingbloom.firebasepractice.common.ext.isValidPassword
import com.codingbloom.firebasepractice.common.ext.passwordMatches
import com.codingbloom.firebasepractice.common.snackbar.SnackbarManager
import com.codingbloom.firebasepractice.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
  private val accountService: AccountService
) : ViewModel() {

  var uiState = mutableStateOf(SignUpUiState())
    private set

  private val email
    get() = uiState.value.email
  private val password
    get() = uiState.value.password

  fun onEmailChange(newValue: String) {
    uiState.value = uiState.value.copy(email = newValue)
  }

  fun onPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(password = newValue)
  }

  fun onRepeatPasswordChange(newValue: String) {
    uiState.value = uiState.value.copy(repeatPassword = newValue)
  }




  fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {

    if (!email.isValidEmail()) {
      SnackbarManager.showMessage(AppText.email_error)
      return
    }

    if (!password.isValidPassword()) {
      SnackbarManager.showMessage(AppText.password_error)
      return
    }

    if (!password.passwordMatches(uiState.value.repeatPassword)) {
      SnackbarManager.showMessage(AppText.password_match_error)
      return
    }

    viewModelScope.launch {
      accountService.linkAccount(email, password)
      openAndPopUp(HOME_SCREEN, SIGN_UP_SCREEN)
    }
  }
}
