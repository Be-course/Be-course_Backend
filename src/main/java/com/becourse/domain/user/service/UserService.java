package com.becourse.domain.user.service;

import com.becourse.domain.user.domain.UserEntity;
import com.becourse.domain.user.dto.CreateUserRequest;
import com.becourse.domain.user.dto.LoginRequest;
import com.becourse.domain.user.dto.UserDto;
import com.becourse.global.common.BaseResponse;

public interface UserService {
    BaseResponse localLogin(LoginRequest loginRequest);

    BaseResponse getUser(Long userId);

    BaseResponse createOAuthUser();

    BaseResponse createLocalUser(CreateUserRequest createUserRequest);

    default UserDto entityToDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .auth(user.getAuth())
                .email(user.getEmail())
                .oAuthId(user.getOAuthId())
                .oAuthProvider(user.getOAuthProvider())
                .password(user.getPassword())
                .userId(user.getUserId())
                .userName(user.getUserName())
                .build();
    }
}
