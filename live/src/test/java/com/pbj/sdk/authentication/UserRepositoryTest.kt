package com.pbj.sdk.authentication

import com.pbj.sdk.concreteImplementation.authentication.UserApi
import com.pbj.sdk.concreteImplementation.authentication.UserRepositoryImpl
import com.pbj.sdk.concreteImplementation.authentication.model.*
import com.pbj.sdk.concreteImplementation.generic.GenericError
import com.pbj.sdk.concreteImplementation.storage.PBJPreferences
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.di.DataModule
import com.pbj.sdk.domain.authentication.LoginError
import com.pbj.sdk.domain.authentication.RegisterError
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.errorResult
import com.pbj.sdk.domain.successResult
import com.pbj.sdk.testUtils.MainCoroutineRule
import com.pbj.sdk.testUtils.runBlockingTest
import com.squareup.moshi.Moshi
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Response
import java.io.File

internal class UserRepositoryTest: KoinTest {

    companion object {
        const val ID = "123"
        const val EMAIL = "email@mail.com"
        const val PASSWORD = "password"
        const val STRING = "string"
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val moshi: Moshi by inject()

    @MockK
    lateinit var api: UserApi

    @MockK
    lateinit var preferences: PBJPreferences

    lateinit var repository: UserRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        startKoin {
            modules(DataModule.init("", ApiEnvironment.DEV))
        }

        repository = UserRepositoryImpl(api, preferences, moshi)
    }

    @After
    fun onFinish() {
        stopKoin()
    }

    @Test
    fun `test on success login returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { api.loginUser(JsonLoginRequest(EMAIL, PASSWORD)) } returns
                Response.success(jsonUser)

        //When
        val result = repository.login(EMAIL, PASSWORD).successResult

        //Then
        result shouldBe user
    }

    @Test
    fun `test on error login returns response with WrongCredentials`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { api.loginUser(JsonLoginRequest(EMAIL, PASSWORD)) } returns
                Response.error(400, "{}".toResponseBody())

        //When
        val result = repository.login(EMAIL, PASSWORD).errorResult

        //Then
        result shouldBe LoginError.WrongCredentials("Response.error()")
    }

    @Test
    fun `test on success register returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { api.registerUser(jsonRegisterRequest) } returns
                Response.success(jsonUser)

        //When
        val result = repository.register(jsonRegisterRequest).successResult

        //Then
        result shouldBe user
    }

    @Test
    fun `test on error register returns response with ValidationError`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { api.registerUser(jsonRegisterRequest) } returns
                Response.error(400, "{}".toResponseBody())

        //When
        val result = repository.register(jsonRegisterRequest).errorResult

        //Then
        result shouldBe RegisterError.ValidationError(null)
    }

    @Test
    fun `test on success getUser returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { api.getUser() } returns
                Response.success(jsonUser)

        //When
        val result = repository.getUser().successResult

        //Then
        result shouldBe user
    }

    @Test
    fun `test on error getUser returns response with ValidationError`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { api.getUser() } returns
                Response.error(403, "{}".toResponseBody())

        //When
        val result = repository.getUser().errorResult

        //Then
        result shouldBe GenericError.NoPermission(null)
    }

    @Test
    fun `test on success getLocallySavedUser returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { preferences.user } returns user

        //When
        val result = repository.getLocallySavedUser().successResult

        //Then
        result shouldBe user
    }

    @Test
    fun `test on error no User saved, getLocallySavedUser returns null`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { preferences.user } returns null

        //When
        val result = repository.getLocallySavedUser().errorResult

        //Then
        result shouldBe null
    }

    @Test
    fun `test on success updateUser returns response with user`() = mainCoroutineRule.runBlockingTest {
        val any = Any()
        //Given
        coEvery { api.updateUser(UpdateProfileRequest(STRING, STRING)) } returns
                Response.success(any)

        //When
        val result = repository.updateUser(STRING, STRING).successResult

        //Then
        result shouldBe any
    }

    @Test
    fun `test on error updateUser returns null`() = mainCoroutineRule.runBlockingTest {
        val any = Any()
        //Given
        coEvery { api.updateUser(UpdateProfileRequest(STRING, STRING)) } returns
                Response.error(402, "{}".toResponseBody())

        //When
        val result = repository.updateUser(STRING, STRING).errorResult

        //Then
        result shouldBe GenericError.Unknown()
    }

    @Test
    fun `test on success changePassword returns ProfileImage`() = mainCoroutineRule.runBlockingTest {
        //Given
        val any = Any()
        val changePasswordRequest = ChangePasswordRequest(STRING, STRING)
        coEvery { api.changePassword(changePasswordRequest) } returns Response.success(any)

        //When
        val result = repository.changePassword(STRING, STRING).successResult

        //Then
        result shouldBe any
    }

    @Test
    fun `test on error changePassword returns GenericError`() = mainCoroutineRule.runBlockingTest {
        //Given
        val file = File(STRING)
        val changePasswordRequest = ChangePasswordRequest(STRING, STRING)
        coEvery { api.changePassword(changePasswordRequest) } returns
                Response.error(408, "{}".toResponseBody())

        //When
        val result = repository.changePassword(STRING, STRING).errorResult

        //Then
        result shouldBe GenericError.Unknown()
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

    private val jsonRegisterRequest = JsonRegisterRequest(
        first_name = STRING,
        last_name = STRING,
        username = STRING,
        email = STRING,
        password = STRING
    )
}