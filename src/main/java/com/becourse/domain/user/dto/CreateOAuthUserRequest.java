package com.becourse.domain.user.dto;

public record CreateOAuthUserRequest(String oAuthId, String oAuthProvider, String userName, String email) {
    public boolean isNull() {
        if(oAuthId == null && oAuthProvider == null && email == null && userName == null) {
            return true;
        } else {
            return false;
        }
    }
}
