package com.example.language_tutor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.language_tutor.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _currentUser = MutableStateFlow<FirebaseUser?>(authRepository.currentUser)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser

    fun register(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = authRepository.registerUser(email, password)
            if (user != null) {
                _currentUser.value = user
                onResult(true, "Регистрация успешна")
            } else {
                onResult(false, "Ошибка регистрации")
            }
        }
    }

    fun login(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = authRepository.loginUser(email, password)
            if (user != null) {
                _currentUser.value = user
                onResult(true, "Вход выполнен")
            } else {
                onResult(false, "Ошибка входа")
            }
        }
    }

    fun logout() {
        authRepository.logout()
        _currentUser.value = null
    }
}
