package com.becourse.domain.user.service;

import com.becourse.domain.user.dto.CreateOAuthUserRequest;
import com.becourse.global.common.BaseResponse;


public interface OAuthService {
    BaseResponse login(String code, String registrationId);

    BaseResponse join(CreateOAuthUserRequest createOAuthUserRequest);
}
