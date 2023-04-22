package com.exraion.beu.viewmodel

import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.ui.splash.SplashUIState
import com.exraion.beu.ui.splash.SplashViewModel
import com.exraion.beu.util.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SplashViewModel
    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        repository = mockk()
        viewModel = SplashViewModel(repository)
    }

    @Test
    fun `when user has run the app before and is logged in, set UI state to LoggedIn`() = runTest {
        // Given
        coEvery { repository.readPrefHaveRunAppBefore() } returns flowOf(true)
        coEvery { repository.readPrefIsLogin() } returns flowOf(true)
        coEvery { repository.fetchUserDetail() } returns flowOf(any())

        // When
        viewModel = SplashViewModel(repository)

        // Then
        verify {
            repository.readPrefHaveRunAppBefore()
            repository.readPrefIsLogin()
            repository.fetchUserDetail()
        }
        Assertions.assertEquals(viewModel.uiState.value, SplashUIState.LoggedIn)
    }

    @Test
    fun `when user has run the app before and is not logged in, set UI state to NotLoggedIn`() = runTest {
        // Given
        coEvery { repository.readPrefHaveRunAppBefore() } returns flowOf(true)
        coEvery { repository.readPrefIsLogin() } returns flowOf(false)

        // When
        viewModel = SplashViewModel(repository)

        // Then
        verify {
            repository.readPrefHaveRunAppBefore()
            repository.readPrefIsLogin()
        }
        Assertions.assertEquals(viewModel.uiState.value, SplashUIState.NotLoggedIn)
    }

    @Test
    fun `when user has not run the app before, set UI state to FirstRun`() = runTest {
        // Given
        coEvery { repository.readPrefHaveRunAppBefore() } returns flowOf(false)

        // When
        viewModel = SplashViewModel(repository)

        // Then4
        verify {
            repository.readPrefHaveRunAppBefore()
        }
        Assertions.assertEquals(viewModel.uiState.value, SplashUIState.FirstRun)
    }

}