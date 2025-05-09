package com.hemonexus.model;

public enum ERole {
    ROLE_ADMIN,

    @Deprecated
    ROLE_BLOOD_BANK_ADMIN,

    @Deprecated // keep if you still need it
    ROLE_USER, // legacy – mark @Deprecated for visibility
    @Deprecated
    ROLE_MODERATOR,

    // legacy – mark @Deprecated
    ROLE_DONOR,
    ROLE_REQUESTER
}
