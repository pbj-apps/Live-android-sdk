package com.pbj.sdk.core

enum class ApiEnvironment {
    DEV,
    DEMO,
    PROD
}

internal val ApiEnvironment.domain: String
    get() = when (this) {
        ApiEnvironment.DEV -> "api.pbj-live.dev.pbj.engineering"
        ApiEnvironment.DEMO -> "api.pbj-live.demo.pbj.engineering"
        ApiEnvironment.PROD -> "api.pbj.live"
    }

internal val ApiEnvironment.isDebug: Boolean
    get() = this == ApiEnvironment.DEV || this == ApiEnvironment.DEMO