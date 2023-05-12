package com.raju.mvvm

import com.raju.mvvm.data.User
import com.raju.mvvm.domain.BaseUseCase
import com.raju.mvvm.presentation.UserViewModel
import com.raju.mvvm.utils.DispatcherProvider
import com.raju.mvvm.utils.TestDispatcherProvider
import com.raju.mvvm.utils.TestUserData
import com.raju.mvvm.utils.UiState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {

    @Mock
    lateinit var useCase: BaseUseCase

    private var dispatcherProvider: DispatcherProvider = TestDispatcherProvider()

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = UserViewModel(useCase, dispatcherProvider)
    }

    @Test
    fun `getUsers() should update uiState with success state when useCase returns data`() =
        runTest {
            // Given
            val flow = flow { emit(TestUserData.users) }

            `when`(useCase.getUsers()).thenReturn(flow)

            // When
            viewModel.getUsers()

            // Then
            val actualUiState = viewModel.uiState.value
            assertEquals(UiState.Success(TestUserData.users), actualUiState)
        }

    @Test
    fun `getUsers() should update uiState with error state when useCase throws an exception`() =
        runTest {
            // Given
            val expectedErrorMessage = "Something went wrong"
            val flow = flow<List<User>> { throw Exception(expectedErrorMessage) }

            `when`(useCase.getUsers()).thenReturn(flow)

            // When
            viewModel.getUsers()

            // Then
            val actualUiState = viewModel.uiState.value
            assertEquals(UiState.Error(expectedErrorMessage), actualUiState)
        }

    @Test
    fun `getUsers() should update uiState with loading state when called`() = runTest {
        // Given
        val flow = flow<List<User>> { emptyList<User>() }

        `when`(useCase.getUsers()).thenReturn(flow)

        // When
        viewModel.getUsers()

        // Then
        val actualUiState = viewModel.uiState.value
        assertEquals(UiState.Loading, actualUiState)
    }

    @Test
    fun `loginUser() should update uiState with success state when useCase returns data`() =
        runTest {
            // Given
            val flow = flow { emit(TestUserData.user) }

            `when`(useCase.login(TestUserData.username, TestUserData.password)).thenReturn(flow)

            // When
            viewModel.login(TestUserData.username, TestUserData.password)

            // Then
            val actualUiState = viewModel.loginUiState.value
            assertEquals(UiState.Success(TestUserData.user), actualUiState)
        }

    @Test
    fun `loginUser() should update uiState with error state when useCase throws an exception`() =
        runTest {

            // Given
            val expectedErrorMessage = "Something went wrong"
            val flow = flow<User> { throw Exception(expectedErrorMessage) }

            `when`(useCase.login(TestUserData.username, TestUserData.password)).thenReturn(flow)

            // When
            viewModel.login(TestUserData.username, TestUserData.password)

            // Then
            val actualUiState = viewModel.loginUiState.value
            assertEquals(UiState.Error(expectedErrorMessage), actualUiState)
        }

    @Test
    fun `loginUser() should update uiState with loading state when called`() = runTest {
        // Given
        val flow = flow<User> { null }

        `when`(useCase.login(TestUserData.username, TestUserData.password)).thenReturn(flow)

        // When
        viewModel.login(TestUserData.username, TestUserData.password)

        // Then
        val actualUiState = viewModel.loginUiState.value
        assertEquals(UiState.Loading, actualUiState)
    }
}
