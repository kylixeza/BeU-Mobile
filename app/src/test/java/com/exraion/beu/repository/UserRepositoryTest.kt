package com.exraion.beu.repository

import com.exraion.beu.data.repository.user.UserRepository
import com.exraion.beu.data.repository.user.UserRepositoryImpl
import com.exraion.beu.data.source.local.LocalDataSource
import com.exraion.beu.data.source.remote.RemoteDataSource
import com.exraion.beu.data.source.remote.RemoteResponse
import com.exraion.beu.data.source.remote.api.model.auth.RegisterBody
import com.exraion.beu.data.source.remote.api.model.auth.TokenResponse
import com.exraion.beu.data.util.Resource
import com.exraion.beu.util.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.capture


@ExperimentalCoroutinesApi
class UserRepositoryTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    private lateinit var localDataSource: LocalDataSource

    @RelaxedMockK
    private lateinit var remoteDataSource: RemoteDataSource

    private lateinit var repository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `should return success`() = runTest {
        val tokenResponse = slot<String>()
        val registerBody = slot<RegisterBody>()

        coEvery { remoteDataSource.signUp(capture(registerBody)) } returns flowOf(RemoteResponse.Success(TokenResponse("myToken")))
        coEvery { localDataSource.savePrefToken(capture(tokenResponse)) } just runs

        val result = repository.signUp(
            RegisterBody(
                "email",
                "password",
                "name",
                "phone",
                "address",
            )
        ).last()

        coVerify(exactly = 1) { remoteDataSource.signUp(any()) }
        coVerify { localDataSource.savePrefToken(any()) }

        Assertions.assertEquals(flowOf(Resource.Success(Unit)).first().data, result.data)
    }

}