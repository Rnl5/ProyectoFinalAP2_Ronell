package com.edu.ucne.proyectofinalap2_ronell.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edu.ucne.proyectofinalap2_ronell.data.local.entities.UsuarioEntity
import com.edu.ucne.proyectofinalap2_ronell.data.repository.UsuarioRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val usuarioRepository: UsuarioRepository,
) : ViewModel() {

    private val _userData = MutableStateFlow<UsuarioEntity?>(null)
    val userData: StateFlow<UsuarioEntity?> = _userData

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.NoAutenticado)
    val authState: StateFlow<AuthState> = _authState

    init {
        viewModelScope.launch {
            checkAuthState()
        }

        viewModelScope.launch {
            authState.collect { state ->
                if(state is AuthState.Autenticado) {
                    fetchUserData()
                }
            }
        }
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val fetchedUser = usuarioRepository.getUserByEmail(currentUser.email ?: "")
                Log.d("AuthViewModel", "Fetched user: $fetchedUser")
//                _userData.value = usuarioRepository.getUserByEmail(currentUser.email ?: "")
                _userData.value = fetchedUser
            }
        }
    }

    fun checkAuthState() {
        _authState.value = if (auth.currentUser == null) {
            AuthState.NoAutenticado
        } else {
            AuthState.Autenticado
        }
    }

    fun login(
        email: String,
        password: String,
    ) {


//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Autenticado
//                } else{
//                    _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Error(task.exception?.message ?: "Something went wrong")
//                }
//            }
//
//    }
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("El email o la contraseña no pueden estar vacios")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Autenticado
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }

    }

    fun signup(
        email: String,
        password: String,
        nombreUsuario: String,
    ) {
        if (email.isEmpty() || password.isEmpty() || nombreUsuario.isEmpty()) {
            _authState.value = AuthState.Error("El email o la contraseña no pueden estar vacios")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = UsuarioEntity(
                        nombreUsuario = nombreUsuario,
                        correoElectronico = email,
                        password = password
                    )
                    viewModelScope.launch {
                        usuarioRepository.saveUsuario(user)
                    }
                    _authState.value = AuthState.Autenticado
                } else {
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }

    }

    fun signout() {
        viewModelScope.launch {
            auth.signOut()
            _authState.value = AuthState.NoAutenticado
            _userData.value = null
        }
//        auth.signOut()
//        _authState.value = AuthState.NoAutenticado
    }


}


sealed class AuthState {
    object Autenticado : AuthState()
    object NoAutenticado : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}


//class AuthViewModel @Inject constructor(
//    private val usuarioRepository: UsuarioRepository
//) : ViewModel() {
//    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
//
//    private val _authState = MutableLiveData<AuthState>()
//    val authState :  LiveData<AuthState> = _authState
//
//    init {
//        checkAuthState()
//    }
//
//    fun checkAuthState() {
//        if(auth.currentUser == null) {
//            _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.NoAutenticado
//        } else {
//            _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Autenticado
//
//        }
//    }
//
//    fun login(
//        email: String,
//        password:String) {
//        if(email.isEmpty() || password.isEmpty()) {
//            _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Error("El email o la contraseña no pueden estar vacios")
//            return
//        }
//        _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Loading
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Autenticado
//                } else{
//                    _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Error(task.exception?.message ?: "Something went wrong")
//                }
//            }
//
//    }
//
//    fun signup(
//        email: String,
//        password:String
//    ) {
//        if(email.isEmpty() || password.isEmpty()) {
//            _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Error("El email o la contraseña no pueden estar vacios")
//            return
//        }
//        _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Loading
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val user = UsuarioEntity(
//                        nombreUsuario = email.substringBefore('@'),
//                        correoElectronico = email,
//                        password = password
//                    )
//                    viewModelScope.launch {
//                        usuarioRepository.saveUsuario(user)
//                    }
//                    _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Autenticado
//                } else{
//                    _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.Error(task.exception?.message ?: "Something went wrong")
//                }
//            }
//
//    }
//
//    fun signout(){
//        auth.signOut()
//        _authState.value = com.edu.ucne.proyectofinalap2_ronell.presentation.login.AuthState.NoAutenticado
//    }
//
//
//}
//
//
//sealed class AuthState{
//    object Autenticado : AuthState()
//    object NoAutenticado : AuthState()
//    object Loading : AuthState()
//    data class Error(val message: String) :AuthState()
//}