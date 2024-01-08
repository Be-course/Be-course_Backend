package com.becourse.domain.user.presentation;

import com.becourse.domain.user.dto.CreateOAuthUserRequest;
import com.becourse.domain.user.exception.UserException;
import com.becourse.domain.user.service.OAuthService;
import com.becourse.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/login/{registrationId}")
    public BaseResponse oAuthLogin(@RequestParam String code, @PathVariable String registrationId) {
        return oAuthService.login(code, registrationId);
    }

    @PostMapping
    public BaseResponse oAuthJoin(@RequestBody CreateOAuthUserRequest createOAuthUserRequest) {
        if (createOAuthUserRequest.isNull()) {
            throw UserException.badRequestException();
        }
        return oAuthService.join(createOAuthUserRequest);
    }
}