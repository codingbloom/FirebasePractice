package com.codingbloom.firebasepractice.screens.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.codingbloom.firebasepractice.Constants.CLIENT_ID
import com.codingbloom.firebasepractice.HOME_SCREEN
import com.codingbloom.firebasepractice.LOGIN_SCREEN
import com.codingbloom.firebasepractice.SIGN_UP_SCREEN
import com.codingbloom.firebasepractice.common.composable.BasicButton
import com.codingbloom.firebasepractice.common.composable.BasicTextButton
import com.codingbloom.firebasepractice.common.composable.BasicToolbar
import com.codingbloom.firebasepractice.common.composable.EmailField
import com.codingbloom.firebasepractice.common.composable.PasswordField
import com.codingbloom.firebasepractice.common.ext.basicButton
import com.codingbloom.firebasepractice.common.ext.fieldModifier
import com.codingbloom.firebasepractice.common.ext.textButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.stevdzasan.onetap.OneTapSignInState
import com.stevdzasan.onetap.OneTapSignInWithGoogle
import com.stevdzasan.onetap.rememberOneTapSignInState
import com.codingbloom.firebasepractice.R.string as AppText


@Composable
fun LoginScreen(
    openScreen: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState
    val oneTapSignInState = rememberOneTapSignInState()
    val context = LocalContext.current

    BasicToolbar(AppText.login_details)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, viewModel::onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, viewModel::onPasswordChange, Modifier.fieldModifier())

        BasicButton(AppText.sign_in, Modifier.basicButton()) { viewModel.onSignInClick(openAndPopUp) }

        BasicTextButton(AppText.create_account, Modifier.textButton()) {
            viewModel.onSignUpClick(openScreen)
        }

        BasicTextButton(AppText.guest_login, Modifier.textButton()) {
            viewModel.createAnonymousAccount(openAndPopUp)
        }

        BasicTextButton(AppText.google_login, Modifier.textButton()) {
            oneTapSignInState.open()
        }
    }


    OneTapSignInWithGoogle(
        state = oneTapSignInState,
        clientId = CLIENT_ID,
        onTokenIdReceived = {tokenId ->

            viewModel.linkWithGoogleAccount(tokenId)
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
            Toast.makeText(context, "Successfully logged in!", Toast.LENGTH_SHORT).show()

        },
        onDialogDismissed = {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    )
}