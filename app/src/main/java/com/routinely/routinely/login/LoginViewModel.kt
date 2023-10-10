package com.routinely.routinely.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.routinely.routinely.data.auth.api.RegisterApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val registerRequest: RegisterApi,
) : ViewModel() {
    var authenticated = mutableStateOf(false)
        private set
    fun loginWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            authenticated.value = true
        }
    }
}