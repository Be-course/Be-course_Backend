package com.becourse.domain.user.dto;

import com.becourse.domain.user.domain.UserAuthority;
import lombok.Builder;

@Builder
public record UserDto (Long id,
                       String userId,
                       String userName,
                       String email,
                       String password,
                       String oAuthProvider,
                       String oAuthId,
                       UserAuthority auth) {

}


