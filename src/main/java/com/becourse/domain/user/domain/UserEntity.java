package com.becourse.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String userName;

    @Column(nullable = false)
    private String email;

    private String password;

    private String oAuthProvider;

    private String oAuthId;

    private UserAuthority auth;

    @Builder
    public UserEntity(String email, String userId, String password, String oAuthProvider, String oAuthId, String userName) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.oAuthProvider = oAuthProvider;
        this.oAuthId = oAuthId;
        this.userName = userName;
        this.auth = UserAuthority.USER;
    }
}
