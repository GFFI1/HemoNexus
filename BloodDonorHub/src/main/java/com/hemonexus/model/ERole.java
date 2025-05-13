package com.hemonexus.model;

public enum ERole {
    ROLE_ADMIN,

    @Deprecated
    ROLE_BLOOD_BANK_ADMIN,

    @Deprecated // reduced the user roles to admin, requester and donor
    ROLE_USER,
    @Deprecated
    ROLE_MODERATOR,

    ROLE_DONOR,
    ROLE_REQUESTER
}
