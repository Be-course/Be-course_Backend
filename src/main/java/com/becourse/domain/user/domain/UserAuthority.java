package com.becourse.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAuthority {
    USER("ROLE_USER"), LECTURER("ROLE_LECTURER"), ADMIN("ROLE_ADMIN");

    private final String authority;
}
