package com.becourse.domain.user.presentation;

import com.becourse.domain.user.dto.CreateOAuthUserRequest;
import com.becourse.domain.user.exception.UserException;
import com.becourse.domain.user.service.OAuthService;
import com.becourse.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
@Tag(name = "OAuth2.0 Controller", description = "소셜 로그인 관련 컨트롤러(로그인, 회원가입)")
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/login/{registrationId}")
    @Operation(summary = "로그인", description = "소셜 로그인 최초 로그인 시 '최초 로그인' 문구와 함께 유저의 데이터 반환 최초 로그인이 아닐 시 토큰 반환")
    public BaseResponse oAuthLogin(@RequestParam String code, @PathVariable String registrationId) {
        return oAuthService.login(code, registrationId);
    }

    @PostMapping
    @Operation(summary = "회원가입", description = "최초 소셜 로그인 시 다른 데이터를 더 받아해서 회원가입을 따로 만듦")
    public BaseResponse oAuthJoin(@RequestBody CreateOAuthUserRequest createOAuthUserRequest) {
        if (createOAuthUserRequest.isNull()) {
            throw UserException.badRequestException();
        }
        return oAuthService.join(createOAuthUserRequest);
    }
}