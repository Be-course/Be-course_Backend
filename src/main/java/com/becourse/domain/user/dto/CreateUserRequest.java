package com.becourse.domain.user.dto;


public record CreateUserRequest(String userId, String password, String email, String userName) {
    public boolean isNull() {
        if(userId == null && password == null && email == null && userName == null) {
            return true;
        } else {
            return false;
        }
    }
}
