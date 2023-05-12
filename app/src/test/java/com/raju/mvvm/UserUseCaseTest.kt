package com.raju.mvvm

import com.raju.mvvm.data.User
import com.raju.mvvm.domain.UserRepository
import com.raju.mvvm.domain.UserUseCase
import com.raju.mvvm.utils.DispatcherProvider
import com.raju.mvvm.utils.TestUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserUseCaseTest {

    @Mock
    lateinit var repository: UserRepository

    @Mock
    lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var userUseCase: UserUseCase

    @Before
    fun setup() {
        userUseCase = UserUseCase(repository = repository, dispatcherProvider = dispatcherProvider)
    }

    @Test
    fun `execute should return flow of users`() = runTest {
        // Given
        val expectedUsers = listOf(User("John"), User("Jane"))

        `when`(repository.getUsers()).thenReturn(expectedUsers)
        `when`(dispatcherProvider.io).thenReturn(Dispatchers.Unconfined)

        // When
        val flow = userUseCase.getUsers()

        // Then
        flow.collect {
            assertEquals(expectedUsers, it)
        }
    }

    @Test
    fun `execute should return empty list on error`() = runTest {
        // Given
        `when`(repository.getUsers()).thenThrow(RuntimeException())
        `when`(dispatcherProvider.io).thenReturn(Dispatchers.Unconfined)

        // When
        val flow = userUseCase.getUsers()

        // Then
        flow.catch { assertTrue(it is RuntimeException) }.collect {
            assertEquals(emptyList<User>(), it)
        }
    }

    @Test
    fun `execute should return flow of user`() = runTest {
        // Given
        `when`(repository.login(TestUserData.username, TestUserData.password)).thenReturn(TestUserData.user)
        `when`(dispatcherProvider.io).thenReturn(Dispatchers.Unconfined)

        // When
        val flow = userUseCase.login(TestUserData.username, TestUserData.password)

        // Then
        flow.collect {
            assertEquals(TestUserData.user, it)
        }
    }

    @Test
    fun `execute should return null on error`() = runTest {
        // Given
        `when`(repository.login(TestUserData.username, TestUserData.password)).thenThrow(RuntimeException())
        `when`(dispatcherProvider.io).thenReturn(Dispatchers.Unconfined)

        // When
        val flow = userUseCase.login(TestUserData.username, TestUserData.password)

        // Then
        flow.catch { assertTrue(it is RuntimeException) }.collect {
            assertEquals(null, it)
        }
    }
}