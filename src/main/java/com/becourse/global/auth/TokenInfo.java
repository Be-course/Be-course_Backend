package com.becourse.global.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
