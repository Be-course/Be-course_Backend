package com.becourse.domain.user.presentation;

import com.becourse.domain.user.dto.CreateUserRequest;
import com.becourse.domain.user.dto.LoginRequest;
import com.becourse.domain.user.exception.UserException;
import com.becourse.domain.user.service.UserServiceImpl;
import com.becourse.global.common.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping
    public BaseResponse join(@RequestBody CreateUserRequest createUserRequest) {
        if(createUserRequest.isNull()) {
            throw UserException.badRequestException();
        }

        return userService.createLocalUser(createUserRequest);
    }

    @PostMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest loginRequest) {
        if(loginRequest.isNull()) {
            throw UserException.badRequestException();
        }

        return userService.localLogin(loginRequest);
    }

    @GetMapping("/{userIdx}")
    public BaseResponse getUser(@PathVariable Long userIdx) {
        return userService.getUser(userIdx);
    }
}
