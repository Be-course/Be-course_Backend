package com.becourse.domain.user.presentation;

import com.becourse.domain.user.dto.*;
import com.becourse.domain.user.exception.UserException;
import com.becourse.domain.user.service.UserService;
import com.becourse.global.auth.TokenInfo;
import com.becourse.global.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "UserController", description = "회원가입, 로그인, 유저 정보 찾기")
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "회원가입")
    public BaseResponse join(@RequestBody CreateUserRequest createUserRequest) {
        if(createUserRequest.isNull()) throw UserException.badRequestException();

        return userService.createLocalUser(createUserRequest);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TokenInfo.class))
            })
    })
    public BaseResponse login(@RequestBody LoginRequest loginRequest) {
        if(loginRequest.isNull()) throw UserException.badRequestException();
        
        return userService.localLogin(loginRequest);
    }

    @GetMapping("/{userIdx}")
    @Operation(summary = "유저 정보 조회", description = "유저의 id 값으로 조회")
    @ApiResponses(value = {
            @ApiResponse(content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))
            })
    })
    public BaseResponse getUser(@PathVariable Long userIdx) {
        return userService.getUser(userIdx);
    }
}
