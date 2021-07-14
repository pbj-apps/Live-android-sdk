package com.pbj.sdk.concreteImplementation.organization.model

import com.pbj.sdk.domain.organization.model.OrganizationConfig

val JsonOrganizationConfig.asModel: OrganizationConfig
    get() = OrganizationConfig(feature_flags.is_chat_enabled)