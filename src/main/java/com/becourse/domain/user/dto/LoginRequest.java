package com.becourse.domain.user.dto;

public record LoginRequest(String userId, String password) {
    public boolean isNull() {
        if(userId == null && password == null) {
            return true;
        } else {
            return false;
        }
    }
}
