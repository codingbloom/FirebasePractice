package com.codingbloom.firebasepractice.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingbloom.firebasepractice.HOME_SCREEN
import com.codingbloom.firebasepractice.R.string as AppText
import com.codingbloom.firebasepractice.LOGIN_SCREEN
import com.codingbloom.firebasepractice.SIGN_UP_SCREEN
import com.codingbloom.firebasepractice.SPLASH_SCREEN
import com.codingbloom.firebasepractice.common.ext.isValidEmail
import com.codingbloom.firebasepractice.common.snackbar.SnackbarManager
import com.codingbloom.firebasepractice.model.service.AccountService
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {


    var uiState = mutableStateOf(LoginUiState())
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

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

       viewModelScope.launch {
           accountService.authenticate(email, password)
           openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
       }
    }

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)


    fun createAnonymousAccount(openAndPopUp: (String, String) -> Unit) {

        viewModelScope.launch {
            accountService.createAnonymousAccount()
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
        }
    }

    fun linkWithGoogleAccount(tokenId: String){

        viewModelScope.launch {
            accountService.linkGoogleAccount(tokenId)
        }
    }


}