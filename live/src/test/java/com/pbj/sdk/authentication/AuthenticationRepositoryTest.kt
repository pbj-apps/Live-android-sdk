package com.pbj.sdk.authentication

import com.pbj.sdk.concreteImplementation.authentication.UserApi
import com.pbj.sdk.concreteImplementation.authentication.model.JsonUser
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.testUtils.MainCoroutineRule
import com.pbj.sdk.testUtils.runBlockingTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class AuthenticationRepositoryTest {

    companion object {
        const val ID = "123"
        const val EMAIL = "email@mail.com"
        const val PASSWORD = "password"
        const val STRING = "string"
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var api: UserApi

    lateinit var repository: UserRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
//        repository = DefaultUserRepository(api)
    }

    @Test
    fun `test on success login returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
//        coEvery { api.loginUser(LoginRequest(EMAIL, PASSWORD)) } returns Response.success(jsonUser)

        //When
//        val result = repository.login(EMAIL, PASSWORD)

        //Then
//        result shouldBe Success(User(ID, STRING, STRING, EMAIL, STRING, false))
    }

    private val jsonUser: JsonUser
        get() = JsonUser(
            ID,
            STRING,
            STRING,
            EMAIL,
            STRING,
            null,
            false,
            false,
            STRING,
            false
        )
}