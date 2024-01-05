package com.becourse.domain.user.service;

import com.becourse.domain.user.domain.UserDetailsImpl;
import com.becourse.domain.user.domain.UserEntity;
import com.becourse.domain.user.domain.repository.UserRepository;
import com.becourse.domain.user.dto.CreateUserRequest;
import com.becourse.domain.user.dto.LoginRequest;
import com.becourse.domain.user.exception.UserException;
import com.becourse.global.auth.JwtProvider;
import com.becourse.global.common.BaseResponse;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public BaseResponse localLogin(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUserId(loginRequest.userId()).get();
        if(bCryptPasswordEncoder.matches(loginRequest.password(), user.getPassword()) && user.getOAuthProvider() == null) {
            UserDetailsImpl userDetails = new UserDetailsImpl(user);
            return new BaseResponse(HttpStatus.OK, "로그인 성공", jwtProvider.generateToken(userDetails));
        } else {
            throw UserException.badRequestException();
        }
    }

    @Override
    public BaseResponse getUser(Long userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw UserException.badRequestException();
        }
        return new BaseResponse(HttpStatus.OK, "조회 성공", entityToDto(user.get()));
    }

    @Override
    public BaseResponse createOAuthUser() {
        return null;
    }

    @Override
    public BaseResponse createLocalUser(CreateUserRequest createUserRequest) {
        userRepository.save(UserEntity.builder()
                .userName(createUserRequest.userName())
                .userId(createUserRequest.userId())
                .password(bCryptPasswordEncoder.encode(createUserRequest.password()))
                .email(createUserRequest.email())
                .build());
        return new BaseResponse(HttpStatus.OK, "회원가입 완료");
    }
}
