package ru.otus.coroutineshomework.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.otus.coroutineshomework.ui.login.data.Credentials

class LoginViewModel : ViewModel() {
    private val api = LoginApi()
    private val _state = MutableLiveData<LoginViewState>(LoginViewState.Login())
    val state: LiveData<LoginViewState> = _state

    /**
     * Login to the network
     * @param name user name
     * @param password user password
     */
    fun login(name: String, password: String) {
        // TODO: Implement login
        _state.value = LoginViewState.LoggingIn
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = api.login(Credentials(name, password))
                _state.postValue(LoginViewState.Content(user))
            } catch (e: Exception) {
                _state.postValue(LoginViewState.Login(e))
            }
        }
    }

    /**
     * Logout from the network
     */
    fun logout() {
        // TODO: Implement logout
        _state.value = LoginViewState.LoggingOut
        viewModelScope.launch(Dispatchers.IO) {
            api.logout()
            _state.postValue(LoginViewState.Login())
        }
    }
}
