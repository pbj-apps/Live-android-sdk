package com.pbj.sdk.concreteImplementation.authentication.model.extensions

import com.pbj.sdk.concreteImplementation.authentication.model.JsonUser
import com.pbj.sdk.domain.authentication.model.User

internal val JsonUser.asModel: User
    get() = User(
        id,
        first_name,
        last_name,
        email,
        username,
        is_survey_attempted,
        profile_image?.image?.small,
        auth_token
    )