
package com.codingbloom.firebasepractice.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.codingbloom.firebasepractice.R.drawable as AppIcon
import com.codingbloom.firebasepractice.R.string as AppText
import com.codingbloom.firebasepractice.common.composable.*
import com.codingbloom.firebasepractice.common.ext.card
import com.codingbloom.firebasepractice.common.ext.fieldModifier
import com.codingbloom.firebasepractice.common.ext.spacer

@Composable
fun HomeScreen(
  openScreen: (String) -> Unit,
  restartApp: (String) -> Unit,
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel = hiltViewModel()
) {

  Column(
    modifier = modifier
      .fillMaxWidth()
      .fillMaxHeight()
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    BasicToolbar(AppText.home)

    Spacer(modifier = Modifier.spacer())


    WantToSignUp{viewModel.goForSignUp(openScreen)}

    SignOutCard { viewModel.onSignOutClick(restartApp) }

    DeleteMyAccountCard { viewModel.onDeleteMyAccountClick(restartApp) }
  }
}

@Composable
private fun WantToSignUp(goForSignUp: () -> Unit) {
  RegularCardEditor(AppText.want_to_sign_up, AppIcon.ic_signin, "", Modifier.card()) {
    goForSignUp()
  }
}

@Composable
private fun SignOutCard(signOut: () -> Unit) {
  var showWarningDialog by remember { mutableStateOf(false) }

  RegularCardEditor(AppText.sign_out, AppIcon.ic_logout, "", Modifier.card()) {
    showWarningDialog = true
  }

  if (showWarningDialog) {
    AlertDialog(
      title = { Text(stringResource(AppText.sign_out_title)) },
      text = { Text(stringResource(AppText.sign_out_description)) },
      dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
      confirmButton = {
        DialogConfirmButton(AppText.sign_out) {
          signOut()
          showWarningDialog = false
        }
      },
      onDismissRequest = { showWarningDialog = false }
    )
  }
}

@Composable
private fun DeleteMyAccountCard(deleteMyAccount: () -> Unit) {
  var showWarningDialog by remember { mutableStateOf(false) }

  DangerousCardEditor(
    AppText.delete_my_account,
    AppIcon.ic_delete_my_account,
    "",
    Modifier.card()
  ) {
    showWarningDialog = true
  }

  if (showWarningDialog) {
    AlertDialog(
      title = { Text(stringResource(AppText.delete_account_title)) },
      text = { Text(stringResource(AppText.delete_account_description)) },
      dismissButton = { DialogCancelButton(AppText.cancel) { showWarningDialog = false } },
      confirmButton = {
        DialogConfirmButton(AppText.delete_my_account) {
          deleteMyAccount()
          showWarningDialog = false
        }
      },
      onDismissRequest = { showWarningDialog = false }
    )
  }
}
