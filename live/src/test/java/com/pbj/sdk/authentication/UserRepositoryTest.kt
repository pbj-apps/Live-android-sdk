package com.pbj.sdk.authentication

import com.pbj.sdk.KoinTestContext
import com.pbj.sdk.concreteImplementation.authentication.UserApi
import com.pbj.sdk.concreteImplementation.authentication.UserRepositoryImpl
import com.pbj.sdk.concreteImplementation.authentication.model.JsonLoginRequest
import com.pbj.sdk.concreteImplementation.authentication.model.JsonUser
import com.pbj.sdk.concreteImplementation.storage.PBJPreferences
import com.pbj.sdk.di.LiveKoinContext
import com.pbj.sdk.domain.authentication.LoginError
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.errorResult
import com.pbj.sdk.domain.successResult
import com.pbj.sdk.testUtils.MainCoroutineRule
import com.pbj.sdk.testUtils.runBlockingTest
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

internal class UserRepositoryTest: KoinTestContext {

    companion object {
        const val ID = "123"
        const val EMAIL = "email@mail.com"
        const val PASSWORD = "password"
        const val STRING = "string"
        const val URL = "www.google.com"
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var api: UserApi

    @MockK
    lateinit var preferences: PBJPreferences

    lateinit var repository: UserRepository

    @Before
    fun setup() {
        LiveKoinContext.koinApp = koinApp

        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(api, preferences)
    }

    @Test
    fun `test on success login returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { api.loginUser(JsonLoginRequest(EMAIL, PASSWORD)) } returns Response.success(
            jsonUser
        )

        //When
        val result = repository.login(EMAIL, PASSWORD).successResult

        //Then
        result shouldBe user
    }

    @Test
    fun `test on error login returns response with WrongCredentials`() = mainCoroutineRule.runBlockingTest {
        val errorMessage = ""
        //Given
        coEvery { api.loginUser(JsonLoginRequest(EMAIL, PASSWORD)) } returns
                Response.error(400, errorMessage.toResponseBody())

        //When
        val result = repository.login(EMAIL, PASSWORD).errorResult

        //Then
        result shouldBe LoginError.WrongCredentials(errorMessage)
    }

    private val jsonUser: JsonUser
        get() = JsonUser(
            id = ID,
            first_name = STRING,
            last_name = STRING,
            email = EMAIL,
            username = STRING,
            profile_image = null,
            is_verified = false,
            is_staff = false,
            auth_token = STRING,
            is_survey_attempted = false
        )

    private val user: User
        get() = User(
            id = ID,
            firstname = STRING,
            lastname = STRING,
            email = EMAIL,
            username = STRING,
            hasAnsweredSurvey = false,
            avatarUrl = null,
            authToken = STRING,
        )
}