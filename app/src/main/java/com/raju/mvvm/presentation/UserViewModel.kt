package com.raju.mvvm.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raju.mvvm.data.User
import com.raju.mvvm.domain.BaseUseCase
import com.raju.mvvm.utils.DispatcherProvider
import com.raju.mvvm.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val baseUseCase: BaseUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<User>>> = _uiState

    private val _loginUiState = MutableStateFlow<UiState<User>>(UiState.Loading)
    val loginUiState: StateFlow<UiState<User>> = _loginUiState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        exception.printStackTrace()
        _uiState.value = UiState.Error(exception.message ?: "Something went wrong")
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(dispatcherProvider.io + exceptionHandler) {
            _loginUiState.value = UiState.Loading
            baseUseCase
                .login(username, password)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _loginUiState.value = UiState.Error(e.message ?: "Something went wrong")
                }
                .collect { users ->
                    _loginUiState.value = UiState.Success(users)
                }
        }
    }

    fun getUsers() {
        viewModelScope.launch(dispatcherProvider.io + exceptionHandler) {
            _uiState.value = UiState.Loading
            baseUseCase
                .getUsers()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Something went wrong")
                }
                .collect { users ->
                    _uiState.value = UiState.Success(users)
                }
        }
    }
}