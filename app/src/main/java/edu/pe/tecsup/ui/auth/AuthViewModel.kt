package edu.pe.tecsup.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val error: String? = null
)

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    
    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState
    
    init {
        _authState.value = AuthState(isAuthenticated = auth.currentUser != null)
    }
    
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _authState.value = AuthState(isAuthenticated = true)
                onSuccess()
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }
    
    fun register(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, error = null)
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _authState.value = AuthState(isAuthenticated = true)
                onSuccess()
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }
    
    fun logout() {
        auth.signOut()
        _authState.value = AuthState()
    }
    
    fun getCurrentUserId(): String? = auth.currentUser?.uid
}
