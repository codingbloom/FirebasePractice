package com.codingbloom.firebasepractice.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingbloom.firebasepractice.HOME_SCREEN
import com.codingbloom.firebasepractice.LOGIN_SCREEN
import com.codingbloom.firebasepractice.SPLASH_SCREEN
import com.codingbloom.firebasepractice.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor( private val accountService: AccountService) : ViewModel(){

    fun onAppStart(openAndPopUp: (String, String) -> Unit) {

        if (accountService.hasUser) openAndPopUp(HOME_SCREEN, SPLASH_SCREEN)
        else openAndPopUp(LOGIN_SCREEN, SPLASH_SCREEN)
    }

}