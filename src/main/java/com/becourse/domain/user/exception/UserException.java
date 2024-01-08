package com.becourse.domain.user.exception;

import com.becourse.global.exception.BusinessException;
import com.becourse.global.exception.error.ErrorCode;
import com.becourse.global.exception.error.ErrorProperty;

public class UserException extends BusinessException {
    private static final UserException BAD_REQUEST = new UserException(ErrorCode.BAD_REQUEST);

    public UserException(ErrorProperty error) { super(error); }

    public static UserException badRequestException() { return BAD_REQUEST; }
}
