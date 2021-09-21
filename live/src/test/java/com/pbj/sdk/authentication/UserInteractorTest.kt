package com.pbj.sdk.authentication

import com.pbj.sdk.concreteImplementation.authentication.UserInteractorImpl
import com.pbj.sdk.concreteImplementation.authentication.model.JsonRegisterRequest
import com.pbj.sdk.concreteImplementation.generic.GenericError
import com.pbj.sdk.core.ApiEnvironment
import com.pbj.sdk.di.DataModule
import com.pbj.sdk.domain.Result
import com.pbj.sdk.domain.authentication.LoginError
import com.pbj.sdk.domain.authentication.UserInteractor
import com.pbj.sdk.domain.authentication.UserRepository
import com.pbj.sdk.domain.authentication.model.RegisterRequest
import com.pbj.sdk.domain.authentication.model.User
import com.pbj.sdk.domain.vod.model.ProfileImage
import com.pbj.sdk.testUtils.MainCoroutineRule
import com.pbj.sdk.testUtils.runBlockingTest
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File

internal class UserInteractorTest : KoinTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    lateinit var repository: UserRepository

    private lateinit var interactor: UserInteractor

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        startKoin {
            modules(DataModule.init("", ApiEnvironment.DEV))
        }

        interactor = UserInteractorImpl(repository)
    }

    @After
    fun onFinish() {
        stopKoin()
    }

    @Test
    fun `test on success login returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { repository.login(EMAIL, PASSWORD) } returns Result.Success(user)

        //When
        interactor.login(EMAIL, PASSWORD) {
            //Then
            it shouldBe user
        }
    }

    @Test
    fun `test on fail login returns response error`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = LoginError.WrongCredentials(null)
        coEvery { repository.login(EMAIL, PASSWORD) } returns Result.Error(error)

        //When
        interactor.login(EMAIL, PASSWORD,
            onError = {
                it shouldBe error
            }) {}
    }

    @Test
    fun `test on success register returns response with user`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery {
                repository.register(JsonRegisterRequest(EMAIL, PASSWORD, STRING, STRING, STRING))
            } returns Result.Success(user)

            //When
            interactor.register(RegisterRequest(EMAIL, PASSWORD, STRING, STRING, STRING)) {
                //Then
                it shouldBe user
            }
        }

    @Test
    fun `test on fail register returns response error`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = LoginError.WrongCredentials(null)
        coEvery {
            repository.register(JsonRegisterRequest(EMAIL, PASSWORD, STRING, STRING, STRING))
        } returns Result.Success(user)

        //When
        interactor.register(RegisterRequest(EMAIL, PASSWORD, STRING, STRING, STRING), onError = {
            it shouldBe error
        }) {}
    }

    @Test
    fun `test on success getUser returns response with user`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { repository.getUser() } returns Result.Success(user)

        //When
        interactor.getUser {
            //Then
            it shouldBe user
        }
    }

    @Test
    fun `test on fail getUser returns response error`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = GenericError.Unknown()
        coEvery { repository.getUser() } returns Result.Error(error)

        //When
        interactor.getUser({
            //Then
            it shouldBe error
        })
    }

    @Test
    fun `test on success getLocalUser returns response with user`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.getLocallySavedUser() } returns Result.Success(user)

            //When
            interactor.getLocalUser {
                //Then
                it shouldBe user
            }
        }

    @Test
    fun `test on fail getLocalUser returns response error`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = GenericError.Unknown()
        coEvery { repository.getLocallySavedUser() } returns Result.Error(error)

        //When
        interactor.getLocalUser({
            //Then
            it shouldBe error
        })
    }

    @Test
    fun `test on success updateUser returns response with user`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.updateUser(STRING, STRING) } returns Result.Success(user)

            //When
            interactor.updateUser(STRING, STRING) {
                //Then
                it shouldBe user
            }
        }

    @Test
    fun `test on fail updateUser returns response error`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = GenericError.Unknown()
        coEvery { repository.updateUser(STRING, STRING) } returns Result.Error(error)

        //When
        interactor.updateUser(STRING, STRING, {
            //Then
            it shouldBe error
        })
    }

    @Test
    fun `test on success uploadProfilePicture returns response with user`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            val image = File(STRING)
            coEvery { repository.uploadProfilePicture(image) } returns Result.Success(profileImage)

            //When
            interactor.updateUser(STRING, STRING) {
                //Then
                it shouldBe user
            }
        }

    @Test
    fun `test on fail uploadProfilePicture returns response error`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            val error = GenericError.Unknown()
            coEvery { repository.updateUser(STRING, STRING) } returns Result.Error(error)

            //When
            interactor.updateUser(STRING, STRING, {
                //Then
                it shouldBe error
            })
        }

    @Test
    fun `test on success changePassword returns onSuccess callback`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            val any = Any()
            coEvery { repository.changePassword(STRING, STRING) } returns Result.Success(any)

            //When
            interactor.changePassword(STRING, STRING) {
                //Then
                shouldBe(true)
            }
        }

    @Test
    fun `test on fail changePassword returns response error`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = GenericError.Unknown()
        coEvery { repository.changePassword(STRING, STRING) } returns Result.Error(error)

        //When
        interactor.changePassword(STRING, STRING, {
            //Then
            it shouldBe error
        })
    }

    @Test
    fun `test on success logout returns response onSuccess callback`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            val any = Any()
            coEvery { repository.logout() } returns Result.Success(any)

            //When
            interactor.logout(null) {
                //Then
                shouldBe(true)
            }
        }

    @Test
    fun `test on fail logout returns response error`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = GenericError.Unknown()
        coEvery { repository.logout() } returns Result.Error(error)

        //When
        interactor.logout({
            //Then
            it shouldBe error
        }) {}
    }

    @Test
    fun `test on success isUserLoggedIn returns true`() = mainCoroutineRule.runBlockingTest {
        //Given
        coEvery { repository.getUserToken() } returns Result.Success(STRING)

        //When
        interactor.isLoggedIn {
            //Then
            it shouldBe true
        }
    }

    @Test
    fun `test on success isUserLoggedIn but no user token returns false`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.getUserToken() } returns Result.Success(null)

            //When
            interactor.isLoggedIn {
                //Then
                it shouldBe true
            }
        }

    @Test
    fun `test on fail isUserLoggedIn returns false`() = mainCoroutineRule.runBlockingTest {
        //Given
        val error = GenericError.Unknown()
        coEvery { repository.getUserToken() } returns Result.Error(error)

        //When
        interactor.isLoggedIn {
            //Then
            it shouldBe false
        }
    }

    @Test
    fun `test on success isLoggedInAsGuest and is guest returns true`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.isLoggedInAsGuest() } returns Result.Success(true)

            //When
            interactor.isLoggedInAsGuest(null) {
                //Then
                it shouldBe true
            }
        }

    @Test
    fun `test on success isLoggedInAsGuest and is not guest returns true`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.isLoggedInAsGuest() } returns Result.Success(false)

            //When
            interactor.isLoggedInAsGuest(null) {
                //Then
                it shouldBe false
            }
        }

    @Test
    fun `test on fail isLoggedInAsGuest returns response error`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            val error = GenericError.Unknown()
            coEvery { repository.isLoggedInAsGuest() } returns Result.Error(error)

            //When
            interactor.isLoggedInAsGuest({
                //Then
                it shouldBe false
            }) {}
        }

    @Test
    fun `test on success saveIsLoggedInAsGuest and is guest returns true`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.saveIsLoggedInAsGuest(true) } returns Result.Success()

            //When
            interactor.saveIsLoggedInAsGuest(true, null) {
                //Then
                shouldBe(true)
            }
        }

    @Test
    fun `test on success saveIsLoggedInAsGuest and is not guest returns false`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.saveIsLoggedInAsGuest(false) } returns Result.Success()

            //When
            interactor.saveIsLoggedInAsGuest(false, null) {
                //Then
                shouldBe(false)
            }
        }

    @Test
    fun `test on fail saveIsLoggedInAsGuest returns response error`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            val error = GenericError.Unknown()
            coEvery { repository.saveIsLoggedInAsGuest(false) } returns Result.Error(error)

            //When
            interactor.saveIsLoggedInAsGuest(false, {
                //Then
                it shouldBe error
            }) {}
        }


    @Test
    fun `test on success updateDeviceRegistrationToken returns onSuccess callback`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            coEvery { repository.updateDeviceRegistrationToken(STRING) } returns Result.Success()

            //When
            interactor.updateDeviceRegistrationToken(STRING, null) {
                //Then
                shouldBe(true)
            }
        }

    @Test
    fun `test on fail updateDeviceRegistrationToken returns response error`() =
        mainCoroutineRule.runBlockingTest {
            //Given
            val error = GenericError.Unknown()
            coEvery { repository.updateDeviceRegistrationToken(STRING) } returns Result.Error(error)

            //When
            interactor.updateDeviceRegistrationToken(STRING, {
                //Then
                it shouldBe error
            }) {}
        }

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

    private val profileImage: ProfileImage
        get() = ProfileImage(id = ID, fullSize = STRING, medium = STRING, small = STRING)

    companion object {
        const val ID = "123"
        const val EMAIL = "email@mail.com"
        const val PASSWORD = "password"
        const val STRING = "string"
    }
}